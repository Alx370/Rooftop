package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.ValutazioneZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import Immobiliaris.Progetto_Rooftop.Repos.RepoValutazioneZona;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementazione del service per la gestione delle valutazioni per zona.
 * Applica validazioni minime e gestisce operazioni di upsert su chiave "zona".
 *
 * Note di design:
 * - La colonna DB "zona" è persistita tramite converter come displayName dell'enum
 *   {@link Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino} (es. "Centro Storico").
 * - L'upsert evita duplicati per zona: se esiste aggiorna, altrimenti crea una nuova riga.
 * - I valori monetari sono validati per non essere negativi; null è consentito.
 */
@Service
@Transactional
public class ServiceValutazioneZonaImpl implements ServiceValutazioneZona {

    @Autowired
    private RepoValutazioneZona repoValutazioneZona;

    public ServiceValutazioneZonaImpl(RepoValutazioneZona repoValutazioneZona) {
        this.repoValutazioneZona = repoValutazioneZona;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValutazioneZona> getAll() {
        // Restituisce tutte le valutazioni presenti senza filtro
        return repoValutazioneZona.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ValutazioneZona getByZona(ZonaProvinciaTorino zona) {
        // Validazione input: la zona è obbligatoria
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        // Ricerca per zona con errore 404 se assente
        return repoValutazioneZona.findByZona(zona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValutazioneZona> getAllByProvincia(String provincia) {
        String prov = normalizeProvincia(provincia);
        return repoValutazioneZona.findAllByProvinciaOrderByZonaAsc(prov);
    }

    @Override
    @Transactional(readOnly = true)
    public ValutazioneZona getByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona) {
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        String prov = normalizeProvincia(provincia);
        return repoValutazioneZona.findByProvinciaAndZona(prov, zona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
    }

    // Maggiorazione fissa ammobiliato: 8%
    private static final BigDecimal FIXED_PERCENT_AMMOBILIATO = new BigDecimal("0.08");

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcolaAffitto(String provincia,
                                     ZonaProvinciaTorino zona,
                                     BigDecimal metriQuadrati,
                                     boolean ammobiliato) {
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        BigDecimal mq = validateMq(metriQuadrati);
        String prov = normalizeProvincia(provincia);

        ValutazioneZona v = (prov != null)
                ? repoValutazioneZona.findByProvinciaAndZona(prov, zona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"))
                : repoValutazioneZona.findByZona(zona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));

        BigDecimal affittoMq = v.getValoreMqAffitto();
        if (affittoMq == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valore mq affitto non disponibile per la zona");
        }

        BigDecimal base = affittoMq.multiply(mq);
        if (ammobiliato) {
            base = base.multiply(BigDecimal.ONE.add(FIXED_PERCENT_AMMOBILIATO));
        }

        return base.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public ValutazioneZona upsert(ValutazioneZona payload) {
        // L'upsert richiede almeno la zona; altri campi possono essere null
        if (payload == null || payload.getZona() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }

        // Se esiste già una valutazione per la zona, aggiornare i campi forniti
        // Cerca prima per provincia+zona se la provincia è presente, altrimenti per sola zona
        ValutazioneZona existing = null;
        String prov = normalizeProvincia(payload.getProvincia());
        if (prov != null) {
            existing = repoValutazioneZona.findByProvinciaAndZona(prov, payload.getZona()).orElse(null);
        }
        if (existing == null) {
            existing = repoValutazioneZona.findByZona(payload.getZona()).orElse(null);
        }
        if (existing != null) {
            if (payload.getValoreMqVendita() != null) {
                existing.setValoreMqVendita(validateNonNegative(payload.getValoreMqVendita(), "valoreMqVendita"));
            }
            if (payload.getValoreMqAffitto() != null) {
                existing.setValoreMqAffitto(validateNonNegative(payload.getValoreMqAffitto(), "valoreMqAffitto"));
            }
            if (payload.getProvincia() != null && !payload.getProvincia().trim().isEmpty()) {
                existing.setProvincia(payload.getProvincia().trim());
            }
            return repoValutazioneZona.save(existing);
        }

        // Altrimenti creare una nuova valutazione con i valori forniti
        ValutazioneZona nuovo = new ValutazioneZona(
                payload.getZona(),
                payload.getValoreMqVendita() != null ? validateNonNegative(payload.getValoreMqVendita(), "valoreMqVendita") : null,
                payload.getValoreMqAffitto() != null ? validateNonNegative(payload.getValoreMqAffitto(), "valoreMqAffitto") : null
        );
        // Imposta la provincia di default se non fornita esplicitamente
        nuovo.setProvincia(prov != null ? prov : "Torino");

        return repoValutazioneZona.save(nuovo);
    }

    @Override
    public ValutazioneZona update(Long id, ValutazioneZona updated) {
        // Aggiornamento per id con validazioni; se non trovato lancia 404
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        ValutazioneZona existing = repoValutazioneZona.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));

        // Aggiorna solo i campi forniti nel payload
        if (updated.getZona() != null) {
            existing.setZona(updated.getZona());
        }
        if (updated.getValoreMqVendita() != null) {
            existing.setValoreMqVendita(validateNonNegative(updated.getValoreMqVendita(), "valoreMqVendita"));
        }
        if (updated.getValoreMqAffitto() != null) {
            existing.setValoreMqAffitto(validateNonNegative(updated.getValoreMqAffitto(), "valoreMqAffitto"));
        }
        if (updated.getProvincia() != null) {
            String prov = normalizeProvincia(updated.getProvincia());
            if (prov != null) {
                existing.setProvincia(prov);
            }
        }
        return repoValutazioneZona.save(existing);
    }

    @Override
    public void delete(Long id) {
        // Cancellazione per id con controllo esistenza; non fa nulla se id null
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        if (!repoValutazioneZona.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata");
        }
        repoValutazioneZona.deleteById(id);
    }

    private BigDecimal validateNonNegative(BigDecimal val, String field) {
        // Permette null; rifiuta numeri negativi con 400 BAD_REQUEST
        if (val == null) {
            return null;
        }
        if (val.signum() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " non puo' essere negativo");
        }
        return val;
    }

    private String normalizeProvincia(String provincia) {
        if (provincia == null) return null;
        String p = provincia.trim();
        return p.isEmpty() ? null : p;
    }

    private BigDecimal validateMq(BigDecimal mq) {
        if (mq == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mq e' richiesto");
        }
        if (mq.signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mq deve essere positivo");
        }
        return mq;
    }

    // Nessuna normalizzazione percentuale: si applica la percentuale fissa ammobiliato.
}
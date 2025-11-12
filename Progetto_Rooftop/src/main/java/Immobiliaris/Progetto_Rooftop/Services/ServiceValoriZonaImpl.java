package Immobiliaris.Progetto_Rooftop.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.ValoriZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import Immobiliaris.Progetto_Rooftop.Repos.RepoValoriZona;

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
public class ServiceValoriZonaImpl implements ServiceValoriZona {

    private RepoValoriZona repoValoriZona;

    public ServiceValoriZonaImpl(RepoValoriZona repoValoriZona) {
        this.repoValoriZona = repoValoriZona;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValoriZona> getAll() {
        // Restituisce tutte le valutazioni presenti senza filtro
        return repoValoriZona.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ValoriZona getByZona(ZonaProvinciaTorino zona) {
        // Validazione input: la zona è obbligatoria
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        // Ricerca per zona con errore 404 se assente
        return repoValoriZona.findByZona(zona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValoriZona> getAllByProvincia(String provincia) {
        String prov = normalizeProvincia(provincia);
        return repoValoriZona.findAllByProvinciaOrderByZonaAsc(prov);
    }

    @Override
    @Transactional(readOnly = true)
    public ValoriZona getByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona) {
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        String prov = normalizeProvincia(provincia);
        return repoValoriZona.findByProvinciaAndZona(prov, zona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
    }

    // Maggiorazione fissa ammobiliato: 8%
    private static final BigDecimal FIXED_PERCENT_AMMOBILIATO = new BigDecimal("0.08");

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcolaAffitto(String provincia,
                                     Integer cap,
                                     ZonaProvinciaTorino zona,
                                     BigDecimal metriQuadrati,
                                     boolean ammobiliato) {
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        BigDecimal mq = validateMq(metriQuadrati);
        String prov = normalizeProvincia(provincia);

        // Se viene fornito un CAP, validarlo e preferire la ricerca che lo include
        Integer c = (cap != null) ? validateCap(cap) : null;

        ValoriZona v;
        if (prov != null && c != null) {
            v = repoValoriZona.findByProvinciaAndZonaAndCap(prov, zona, c)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
        } else if (prov != null) {
            v = repoValoriZona.findByProvinciaAndZona(prov, zona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
        } else if (c != null) {
            v = repoValoriZona.findByZonaAndCap(zona, c)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
        } else {
            v = repoValoriZona.findByZona(zona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
        }

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
    public ValoriZona upsert(ValoriZona payload) {
        // L'upsert richiede almeno la zona; altri campi possono essere null
        if (payload == null || payload.getZona() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }

        // CAP è richiesto dal modello
        if (payload.getCap() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP e' richiesto");
        }

        // Se esiste già una valutazione per la zona, aggiornare i campi forniti
        // Cerca prima per provincia+zona se la provincia è presente, altrimenti per sola zona
        ValoriZona existing = null;
        String prov = normalizeProvincia(payload.getProvincia());
        Integer c = validateCap(payload.getCap());
        if (prov != null) {
            existing = repoValoriZona.findByProvinciaAndZonaAndCap(prov, payload.getZona(), c).orElse(null);
        }
        if (existing == null) {
            existing = repoValoriZona.findByZonaAndCap(payload.getZona(), c).orElse(null);
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
            if (payload.getCap() != null) {
                existing.setCap(c);
            }
            return repoValoriZona.save(existing);
        }

        // Altrimenti creare una nuova valutazione con i valori forniti
        ValoriZona nuovo = new ValoriZona(
                payload.getZona(),
                payload.getValoreMqVendita() != null ? validateNonNegative(payload.getValoreMqVendita(), "valoreMqVendita") : null,
                payload.getValoreMqAffitto() != null ? validateNonNegative(payload.getValoreMqAffitto(), "valoreMqAffitto") : null
        );
        // Imposta la provincia di default se non fornita esplicitamente
        nuovo.setProvincia(prov != null ? prov : "Torino");
        // Imposta il CAP (obbligatorio)
        nuovo.setCap(c);

        return repoValoriZona.save(nuovo);
    }

    @Override
    public ValoriZona update(Long id, ValoriZona updated) {
        // Aggiornamento per id con validazioni; se non trovato lancia 404
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        ValoriZona existing = repoValoriZona.findById(id)
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
        if (updated.getCap() != null) {
            existing.setCap(validateCap(updated.getCap()));
        }
        return repoValoriZona.save(existing);
    }

    @Override
    public void delete(Long id) {
        // Cancellazione per id con controllo esistenza; non fa nulla se id null
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        if (!repoValoriZona.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata");
        }
        repoValoriZona.deleteById(id);
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

    private Integer validateCap(Integer cap) {
        if (cap == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP e' richiesto");
        }
        if (cap <= 0 || cap > 99999) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP non valido");
        }
        return cap;
    }

    // Nessuna normalizzazione percentuale: si applica la percentuale fissa ammobiliato.

    public RepoValoriZona getRepoValoriZona() {
        return repoValoriZona;
    }

    public void setRepoValoriZona(RepoValoriZona repoValoriZona) {
        this.repoValoriZona = repoValoriZona;
    }
}
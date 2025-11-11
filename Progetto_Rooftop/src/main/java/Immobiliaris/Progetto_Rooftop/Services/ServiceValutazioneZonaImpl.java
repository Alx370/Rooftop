package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.ValutazioneZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import Immobiliaris.Progetto_Rooftop.Repos.RepoValutazioneZona;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementazione del service per la gestione delle valutazioni per zona.
 * Applica validazioni minime e gestisce operazioni di upsert su chiave "zona".
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
        return repoValutazioneZona.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ValutazioneZona getByZona(ZonaProvinciaTorino zona) {
        if (zona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }
        return repoValutazioneZona.findByZona(zona)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));
    }

    @Override
    public ValutazioneZona upsert(ValutazioneZona payload) {
        if (payload == null || payload.getZona() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona e' richiesta");
        }

        ValutazioneZona existing = repoValutazioneZona.findByZona(payload.getZona()).orElse(null);
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

        ValutazioneZona nuovo = new ValutazioneZona(
                payload.getZona(),
                payload.getValoreMqVendita() != null ? validateNonNegative(payload.getValoreMqVendita(), "valoreMqVendita") : null,
                payload.getValoreMqAffitto() != null ? validateNonNegative(payload.getValoreMqAffitto(), "valoreMqAffitto") : null
        );
        // set default provincia se non fornita
        nuovo.setProvincia(payload.getProvincia() != null && !payload.getProvincia().trim().isEmpty()
                ? payload.getProvincia().trim()
                : "Torino");

        return repoValutazioneZona.save(nuovo);
    }

    @Override
    public ValutazioneZona update(Long id, ValutazioneZona updated) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        ValutazioneZona existing = repoValutazioneZona.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata"));

        if (updated.getZona() != null) {
            existing.setZona(updated.getZona());
        }
        if (updated.getValoreMqVendita() != null) {
            existing.setValoreMqVendita(validateNonNegative(updated.getValoreMqVendita(), "valoreMqVendita"));
        }
        if (updated.getValoreMqAffitto() != null) {
            existing.setValoreMqAffitto(validateNonNegative(updated.getValoreMqAffitto(), "valoreMqAffitto"));
        }
        if (updated.getProvincia() != null && !updated.getProvincia().trim().isEmpty()) {
            existing.setProvincia(updated.getProvincia().trim());
        }
        return repoValutazioneZona.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id e' richiesto");
        }
        if (!repoValutazioneZona.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Valutazione zona non trovata");
        }
        repoValutazioneZona.deleteById(id);
    }

    private BigDecimal validateNonNegative(BigDecimal val, String field) {
        if (val == null) {
            return null;
        }
        if (val.signum() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " non puo' essere negativo");
        }
        return val;
    }
}
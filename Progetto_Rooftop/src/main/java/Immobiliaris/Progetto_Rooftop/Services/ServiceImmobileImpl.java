package Immobiliaris.Progetto_Rooftop.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.StatoAnnuncio;
import Immobiliaris.Progetto_Rooftop.Model.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Repos.RepoImmobili;

/**
 * Implementazione del servizio per la gestione degli Immobili.
 * Esegue validazioni, imposta valori di default e delega le operazioni al repository.
 */
@Service
@Transactional
public class ServiceImmobileImpl implements ServiceImmobile {

    @Autowired
    private RepoImmobili repoImmobili;

    public ServiceImmobileImpl(RepoImmobili repoImmobili) {
        this.repoImmobili = repoImmobili;
    }

    /** Valida i campi obbligatori dell'immobile. */
    private void validate(Immobile immobile) {
        if (immobile.getId_proprietario() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proprietario e richiesto");
        }
        if (immobile.getTitolo() == null || immobile.getTitolo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titolo e richiesto");
        }
        if (immobile.getIndirizzo() == null || immobile.getIndirizzo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indirizzo e richiesto");
        }
        if (immobile.getCitta() == null || immobile.getCitta().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citta e richiesta");
        }
        if (immobile.getProvincia() == null || immobile.getProvincia().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provincia e richiesta");
        }
        if (immobile.getCap() == null || immobile.getCap().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP e richiesto");
        }
        if (immobile.getTipologia() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipologia e richiesta");
        }
        if (immobile.getMetri_quadri() == null || immobile.getMetri_quadri().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metri quadri devono essere maggiori di zero");
        }
        if (immobile.getPrezzo_richiesto() == null || immobile.getPrezzo_richiesto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo richiesto deve essere maggiore di zero");
        }
    }

    @Override
    public Immobile create(Immobile immobile) {
        validate(immobile);
        
        // Set default values if not provided
        if (immobile.getCreato_il() == null) {
            immobile.setCreato_il(LocalDateTime.now());
        }
        if (immobile.getStato_annuncio() == null) {
            immobile.setStato_annuncio(StatoAnnuncio.VALUTAZIONE);
        }
        if (immobile.getStato_immobile() == null) {
            immobile.setStato_immobile(StatoImmobile.BUONO);
        }

        // Normalize text fields
        immobile.setTitolo(immobile.getTitolo().trim());
        immobile.setIndirizzo(immobile.getIndirizzo().trim());
        immobile.setCitta(immobile.getCitta().trim());
        immobile.setProvincia(immobile.getProvincia().trim().toUpperCase()); // Province codes are uppercase
        immobile.setCap(immobile.getCap().trim());
        
        if (immobile.getDescrizione() != null) {
            immobile.setDescrizione(immobile.getDescrizione().trim());
        }
        if (immobile.getCivico() != null) {
            immobile.setCivico(immobile.getCivico().trim());
        }
        if (immobile.getQuartiere() != null) {
            immobile.setQuartiere(immobile.getQuartiere().trim());
        }
        if (immobile.getPiano() != null) {
            immobile.setPiano(immobile.getPiano().trim());
        }

        return repoImmobili.save(immobile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Immobile> getAll() {
        return repoImmobili.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Immobile getById(int id) {
        return repoImmobili.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Immobile non trovato"));
    }

    @Override
    public Immobile update(int id, Immobile updated) {
        Immobile existing = getById(id);

        // Update only non-null fields
        if (updated.getId_proprietario() != null) {
            existing.setId_proprietario(updated.getId_proprietario());
        }
        if (updated.getId_agente() != null) {
            existing.setId_agente(updated.getId_agente());
        }
        if (updated.getTitolo() != null && !updated.getTitolo().trim().isEmpty()) {
            existing.setTitolo(updated.getTitolo().trim());
        }
        if (updated.getDescrizione() != null) {
            existing.setDescrizione(updated.getDescrizione().trim());
        }
        if (updated.getIndirizzo() != null && !updated.getIndirizzo().trim().isEmpty()) {
            existing.setIndirizzo(updated.getIndirizzo().trim());
        }
        if (updated.getCivico() != null) {
            existing.setCivico(updated.getCivico().trim());
        }
        if (updated.getCitta() != null && !updated.getCitta().trim().isEmpty()) {
            existing.setCitta(updated.getCitta().trim());
        }
        if (updated.getProvincia() != null && !updated.getProvincia().trim().isEmpty()) {
            existing.setProvincia(updated.getProvincia().trim().toUpperCase());
        }
        if (updated.getCap() != null && !updated.getCap().trim().isEmpty()) {
            existing.setCap(updated.getCap().trim());
        }
        if (updated.getQuartiere() != null) {
            existing.setQuartiere(updated.getQuartiere().trim());
        }
        if (updated.getTipologia() != null) {
            existing.setTipologia(updated.getTipologia());
        }
        if (updated.getMetri_quadri() != null && updated.getMetri_quadri().compareTo(java.math.BigDecimal.ZERO) > 0) {
            existing.setMetri_quadri(updated.getMetri_quadri());
        }
        if (updated.getPiano() != null) {
            existing.setPiano(updated.getPiano().trim());
        }
        if (updated.getAnno_costruzione() != null) {
            existing.setAnno_costruzione(updated.getAnno_costruzione());
        }
        if (updated.getPrezzo_richiesto() != null && updated.getPrezzo_richiesto().compareTo(java.math.BigDecimal.ZERO) > 0) {
            existing.setPrezzo_richiesto(updated.getPrezzo_richiesto());
        }
        if (updated.getStato_immobile() != null) {
            existing.setStato_immobile(updated.getStato_immobile());
        }
        if (updated.getStato_annuncio() != null) {
            existing.setStato_annuncio(updated.getStato_annuncio());
        }

        return repoImmobili.save(existing);
    }

    @Override
    public void delete(int id) {
        if (!repoImmobili.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Immobile non trovato");
        }
        repoImmobili.deleteById(id);
    }

    public RepoImmobili getRepoImmobili() {
        return repoImmobili;
    }

    public void setRepoImmobili(RepoImmobili repoImmobili) {
        this.repoImmobili = repoImmobili;
    }
}
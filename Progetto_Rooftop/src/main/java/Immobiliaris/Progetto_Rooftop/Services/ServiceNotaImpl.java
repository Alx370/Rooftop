package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.TipoNota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Repos.RepoNota;

/**
 * Implementazione del servizio per la gestione delle Note interne.
 * Esegue validazioni, imposta valori di default e delega le operazioni al repository.
 */
@Service
@Transactional
public class ServiceNotaImpl implements ServiceNota {

    @Autowired
    private RepoNota notaRepo;

    public ServiceNotaImpl(RepoNota notaRepo) {
        this.notaRepo = notaRepo;
    }

    /** Valida i campi obbligatori della nota. */
    private void validate(Nota n) {
        if (n.getAgente() == null || n.getAgente().getId_utente() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agente e richiesto");
        }
        if (n.getContenuto() == null || n.getContenuto().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contenuto e richiesto");
        }
    }

    @Override
    /** Crea una nota applicando validazioni e valori di default. */
    public Nota create(Nota nota) {
        validate(nota);
        // Defaults
        if (nota.getTipo() == null) nota.setTipo(TipoNota.INTERNO);
        if (nota.getVisibilita() == null) nota.setVisibilita(VisibilitaNota.TEAM);
        // Normalize
        nota.setContenuto(nota.getContenuto().trim());
        return notaRepo.save(nota);
    }

    @Override
    @Transactional(readOnly = true)
    /** Restituisce tutte le note ordinate per data di creazione decrescente. */
    public List<Nota> getAllOrdered() {
        return notaRepo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    /** Restituisce una nota per id oppure NOT_FOUND se non esiste. */
    public Nota getById(Integer id) {
        return notaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    /** Restituisce le note filtrate per id agente. */
    public List<Nota> getByAgente(Integer idAgente) {
        return notaRepo.findAllByAgente_Id_utenteOrderByCreatedAtDesc(idAgente);
    }

    @Override
    @Transactional(readOnly = true)
    /** Restituisce le note filtrate per id immobile. */
    public List<Nota> getByImmobile(Integer idImmobile) {
        return notaRepo.findAllByIdImmobileOrderByCreatedAtDesc(idImmobile);
    }

    @Override
    @Transactional(readOnly = true)
    /** Restituisce le note filtrate per id immobile e visibilità. */
    public List<Nota> getByImmobileAndVisibilita(Integer idImmobile, VisibilitaNota visibilita) {
        return notaRepo.findAllByIdImmobileAndVisibilitaOrderByCreatedAtDesc(idImmobile, visibilita);
    }

    @Override
    /** Aggiorna una nota esistente con i campi forniti. */
    public Nota update(Integer id, Nota updated) {
        Nota existing = getById(id);
        if (updated.getContenuto() != null) {
            String c = updated.getContenuto().trim();
            if (c.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contenuto e richiesto");
            }
            existing.setContenuto(c);
        }
        if (updated.getVisibilita() != null) {
            existing.setVisibilita(updated.getVisibilita());
        }
        if (updated.getTipo() != null) {
            existing.setTipo(updated.getTipo());
        }
        if (updated.getIdImmobile() != null) {
            existing.setIdImmobile(updated.getIdImmobile());
        }
        if (updated.getAgente() != null && updated.getAgente().getId_utente() != 0) {
            existing.setAgente(updated.getAgente());
        }
        return notaRepo.save(existing);
    }

    @Override
    /** Elimina una nota per id oppure NOT_FOUND se non esiste. */
    public void delete(Integer id) {
        if (!notaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota non trovata");
        }
        notaRepo.deleteById(id);
    }

    public RepoNota getNotaRepo() {
        return notaRepo;
    }

    public void setNotaRepo(RepoNota notaRepo) {
        this.notaRepo = notaRepo;
    }
}
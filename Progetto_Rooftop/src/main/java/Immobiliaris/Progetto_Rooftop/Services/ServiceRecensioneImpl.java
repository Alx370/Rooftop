package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Recensione;
import Immobiliaris.Progetto_Rooftop.Repos.RepoRecensione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service layer implementation for managing Recensioni.
 * Applies validation and normalizes payloads before persistence.
 */
@Service
@Transactional
public class ServiceRecensioneImpl implements ServiceRecensione {

    @Autowired
    private RepoRecensione recensioneRepo;

    public ServiceRecensioneImpl(RepoRecensione recensioneRepo) {
        this.recensioneRepo = recensioneRepo;
    }

    /** Validate core fields. */
    private void validate(Recensione r) {
        if (r.getAgente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agente e richiesto");
        }
        if (r.getCommento() == null || r.getCommento().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Commento e richiesto");
        }
        if (r.getRating() < 1 || r.getRating() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating deve essere tra 1 e 5");
        }
    }

    @Override
    public Recensione create(Recensione recensione) {
        validate(recensione);
        // Normalize text fields
        if (recensione.getNome_cliente() != null) recensione.setNome_cliente(recensione.getNome_cliente().trim());
        if (recensione.getTitolo() != null) recensione.setTitolo(recensione.getTitolo().trim());
        recensione.setCommento(recensione.getCommento().trim());
        return recensioneRepo.save(recensione);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recensione> getAllOrdered() {
        return recensioneRepo.findAllByOrderByCreated_atDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Recensione getById(Integer id) {
        return recensioneRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recensione non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recensione> getByAgente(Integer idAgente) {
        return recensioneRepo.findAllByAgente_Id_utenteOrderByCreated_atDesc(idAgente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recensione> getByImmobile(Integer idImmobile) {
        return recensioneRepo.findAllById_immobileOrderByCreated_atDesc(idImmobile);
    }

    @Override
    public Recensione update(Integer id, Recensione updated) {
        Recensione existing = getById(id);

        if (updated.getAgente() != null) {
            existing.setAgente(updated.getAgente());
        }
        if (updated.getId_immobile() != null) {
            existing.setId_immobile(updated.getId_immobile());
        }
        if (updated.getNome_cliente() != null) {
            existing.setNome_cliente(updated.getNome_cliente().trim());
        }
        if (updated.getTitolo() != null) {
            existing.setTitolo(updated.getTitolo().trim());
        }
        if (updated.getCommento() != null) {
            String c = updated.getCommento().trim();
            if (c.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Commento e richiesto");
            }
            existing.setCommento(c);
        }
        if (updated.getRating() != 0) { // 0 means not provided; rating is primitive int
            if (updated.getRating() < 1 || updated.getRating() > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating deve essere tra 1 e 5");
            }
            existing.setRating(updated.getRating());
        }
        existing.setVerificata(updated.isVerificata());

        return recensioneRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!recensioneRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recensione non trovata");
        }
        recensioneRepo.deleteById(id);
    }

    @Override
    public Recensione setVerificata(Integer id, boolean verificata) {
        Recensione r = getById(id);
        r.setVerificata(verificata);
        return recensioneRepo.save(r);
    }
}
package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.TipoNota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Repos.RepoNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service implementation for Note management.
 */
@Service
@Transactional
public class ServiceNotaImpl implements ServiceNota {

    @Autowired
    private RepoNota notaRepo;

    public ServiceNotaImpl(RepoNota notaRepo) {
        this.notaRepo = notaRepo;
    }

    private void validate(Nota n) {
        if (n.getAgente() == null || n.getAgente().getId_utente() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agente e richiesto");
        }
        if (n.getContenuto() == null || n.getContenuto().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contenuto e richiesto");
        }
    }

    @Override
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
    public List<Nota> getAllOrdered() {
        return notaRepo.findAllByOrderByCreated_atDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Nota getById(Integer id) {
        return notaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> getByAgente(Integer idAgente) {
        return notaRepo.findAllByAgente_Id_utenteOrderByCreated_atDesc(idAgente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> getByImmobile(Integer idImmobile) {
        return notaRepo.findAllById_immobileOrderByCreated_atDesc(idImmobile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> getByImmobileAndVisibilita(Integer idImmobile, VisibilitaNota visibilita) {
        return notaRepo.findAllById_immobileAndVisibilitaOrderByCreated_atDesc(idImmobile, visibilita);
    }

    @Override
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
        if (updated.getId_immobile() != null) {
            existing.setId_immobile(updated.getId_immobile());
        }
        if (updated.getAgente() != null && updated.getAgente().getId_utente() != 0) {
            existing.setAgente(updated.getAgente());
        }
        return notaRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!notaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota non trovata");
        }
        notaRepo.deleteById(id);
    }
}
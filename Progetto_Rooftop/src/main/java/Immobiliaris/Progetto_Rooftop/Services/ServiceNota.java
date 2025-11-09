package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing Note (internal notes).
 * Applies validation and delegates persistence to the repository.
 */
@Service
public interface ServiceNota {

    /** Create a new note with validation and defaults. */
    Nota create(Nota nota);

    /** Returns all notes ordered by creation timestamp descending. */
    List<Nota> getAllOrdered();

    /** Retrieves a note by its ID or throws NOT_FOUND. */
    Nota getById(Integer id);

    /** Retrieves notes by agent id ordered by creation timestamp descending. */
    List<Nota> getByAgente(Integer idAgente);

    /** Retrieves notes by immobile id ordered by creation timestamp descending. */
    List<Nota> getByImmobile(Integer idImmobile);

    /** Retrieves notes by immobile id filtered by visibility ordered by creation timestamp descending. */
    List<Nota> getByImmobileAndVisibilita(Integer idImmobile, VisibilitaNota visibilita);

    /** Updates an existing note with the given payload. */
    Nota update(Integer id, Nota updated);

    /** Deletes a note by ID or throws NOT_FOUND. */
    void delete(Integer id);
}
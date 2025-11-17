package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Recensione;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface defining business operations for Recensione management.
 * Implementations should validate payloads and delegate persistence to repositories.
 */
@Service
public interface ServiceRecensione {

    /** Creates a new review. */
    Recensione create(Recensione recensione);

    /** Returns all reviews ordered by creation timestamp descending. */
    List<Recensione> getAllOrdered();

    /** Retrieves a review by its ID. */
    Recensione getById(Integer id);

    /** Retrieves reviews by agent id ordered by creation timestamp descending. */
    List<Recensione> getByAgente(Integer idAgente);

    /** Retrieves reviews by immobile id ordered by creation timestamp descending. */
    List<Recensione> getByImmobile(Integer idImmobile);

    /** Updates an existing review with the given payload. */
    Recensione update(Integer id, Recensione updated);

    /** Deletes a review by ID. */
    void delete(Integer id);

    /** Marks a review as verified or unverified. */
    Recensione setVerificata(Integer id, boolean verificata);
}
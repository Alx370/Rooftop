package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Recensione;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Recensione entity.
 * Provides derived queries for common filters and ordering.
 */
@Repository
public interface RepoRecensione extends JpaRepository<Recensione, Integer> {

    /** Returns all reviews ordered by creation timestamp descending. */
    List<Recensione> findAllByOrderByCreated_atDesc();

    /** Returns reviews by agent ordered by creation timestamp descending. */
    List<Recensione> findAllByAgenteOrderByCreated_atDesc(Utente agente);

    /** Returns reviews by agent id ordered by creation timestamp descending. */
    List<Recensione> findAllByAgente_Id_utenteOrderByCreated_atDesc(Integer id_utente);

    /** Returns reviews by immobile id ordered by creation timestamp descending. */
    List<Recensione> findAllById_immobileOrderByCreated_atDesc(Integer id_immobile);

    /** Returns reviews filtered by verification status ordered by creation timestamp descending. */
    List<Recensione> findAllByVerificataOrderByCreated_atDesc(boolean verificata);
}
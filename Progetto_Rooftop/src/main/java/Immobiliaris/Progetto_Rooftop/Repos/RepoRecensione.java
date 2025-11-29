package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Recensione;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Recensione entity.
 * Provides derived queries for common filters and ordering.
 */
@Repository
public interface RepoRecensione extends JpaRepository<Recensione, Integer> {

    /** Returns all reviews ordered by creation timestamp descending. */
    @Query("SELECT r FROM Recensione r ORDER BY r.created_at DESC")
    List<Recensione> findAllByOrderByCreated_atDesc();

    /** Returns reviews by agent ordered by creation timestamp descending. */
    @Query("SELECT r FROM Recensione r WHERE r.agente = :agente ORDER BY r.created_at DESC")
    List<Recensione> findAllByAgenteOrderByCreated_atDesc(@Param("agente") Utente agente);

    /** Returns reviews by agent id ordered by creation timestamp descending. */
    @Query("SELECT r FROM Recensione r WHERE r.agente.id_utente = :idUtente ORDER BY r.created_at DESC")
    List<Recensione> findAllByAgente_Id_utenteOrderByCreated_atDesc(@Param("idUtente") Integer id_utente);

    /** Returns reviews by immobile id ordered by creation timestamp descending. */
    @Query("SELECT r FROM Recensione r WHERE r.id_immobile = :idImmobile ORDER BY r.created_at DESC")
    List<Recensione> findAllById_immobileOrderByCreated_atDesc(@Param("idImmobile") Integer id_immobile);

    /** Returns reviews filtered by verification status ordered by creation timestamp descending. */
    @Query("SELECT r FROM Recensione r WHERE r.verificata = :verificata ORDER BY r.created_at DESC")
    List<Recensione> findAllByVerificataOrderByCreated_atDesc(@Param("verificata") boolean verificata);
}
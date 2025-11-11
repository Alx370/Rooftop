package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Spring Data JPA per l'entità {@link Immobiliaris.Progetto_Rooftop.Model.Nota}.
 * Fornisce metodi derivati per ordinamento e filtri su agente, immobile e visibilità.
 */
@Repository
public interface RepoNota extends JpaRepository<Nota, Integer> {

    /** Restituisce tutte le note ordinate per data di creazione decrescente. */
    List<Nota> findAllByOrderByCreated_atDesc();

    /** Restituisce le note dell'agente specificato, ordinate per data di creazione decrescente. */
    List<Nota> findAllByAgenteOrderByCreated_atDesc(Utente agente);

    /** Restituisce le note filtrate per id agente, ordinate per data di creazione decrescente. */
    List<Nota> findAllByAgente_Id_utenteOrderByCreated_atDesc(Integer idAgente);

    /** Restituisce le note filtrate per id immobile, ordinate per data di creazione decrescente. */
    List<Nota> findAllById_immobileOrderByCreated_atDesc(Integer idImmobile);

    /** Restituisce le note filtrate per id immobile e visibilità, ordinate per data di creazione decrescente. */
    List<Nota> findAllById_immobileAndVisibilitaOrderByCreated_atDesc(Integer idImmobile, VisibilitaNota visibilita);
}
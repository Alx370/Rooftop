package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Spring Data JPA per l'entità {@link Immobiliaris.Progetto_Rooftop.Model.Nota}.
 * Fornisce metodi derivati per ordinamento e filtri su agente, immobile e visibilità.
 */
@Repository
public interface RepoNota extends JpaRepository<Nota, Integer> {

    /** Restituisce tutte le note ordinate per data di creazione decrescente. */
    List<Nota> findAllByOrderByCreatedAtDesc();

    /** Restituisce le note dell'agente specificato, ordinate per data di creazione decrescente. */
    List<Nota> findAllByAgenteOrderByCreatedAtDesc(Utente agente);

    /** Restituisce le note filtrate per id agente, ordinate per data di creazione decrescente. */
    @Query("SELECT n FROM Nota n WHERE n.agente.id_utente = :idAgente ORDER BY n.createdAt DESC")
    List<Nota> findAllByAgente_Id_utenteOrderByCreatedAtDesc(@Param("idAgente") Integer idAgente);
    
    /** Restituisce le note filtrate per id immobile, ordinate per data di creazione decrescente. */
    List<Nota> findAllByIdImmobileOrderByCreatedAtDesc(Integer idImmobile);

    /** Restituisce le note filtrate per id immobile e visibilità, ordinate per data di creazione decrescente. */
    List<Nota> findAllByIdImmobileAndVisibilitaOrderByCreatedAtDesc(Integer idImmobile, VisibilitaNota visibilita);
}
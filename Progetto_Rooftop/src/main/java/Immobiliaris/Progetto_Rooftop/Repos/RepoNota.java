package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoNota extends JpaRepository<Nota, Integer> {

    List<Nota> findAllByOrderByCreatedAtDesc();

    List<Nota> findAllByAgente_IdUtenteOrderByCreatedAtDesc(Integer idAgente);

    List<Nota> findAllByIdImmobileOrderByCreatedAtDesc(Integer idImmobile);

    List<Nota> findAllByIdImmobileAndVisibilitaOrderByCreatedAtDesc(Integer idImmobile, VisibilitaNota visibilita);
}
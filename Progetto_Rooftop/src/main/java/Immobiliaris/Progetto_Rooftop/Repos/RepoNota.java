package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoNota extends JpaRepository<Nota, Integer> {

    List<Nota> findAllByOrderByCreated_atDesc();

    List<Nota> findAllByAgenteOrderByCreated_atDesc(Utente agente);

    List<Nota> findAllByAgente_Id_utenteOrderByCreated_atDesc(Integer idAgente);

    List<Nota> findAllById_immobileOrderByCreated_atDesc(Integer idImmobile);

    List<Nota> findAllById_immobileAndVisibilitaOrderByCreated_atDesc(Integer idImmobile, VisibilitaNota visibilita);
}
package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.DynFaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepoDynFaq extends JpaRepository<DynFaq, Integer> {

    List<DynFaq> findAllByOrderByOrdineAsc();
    
    List<DynFaq> findAllByCategoriaOrderByOrdineAsc(CategoriaFaq categoria);

}
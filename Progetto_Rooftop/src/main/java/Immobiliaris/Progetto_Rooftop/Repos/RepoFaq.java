package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoFaq extends JpaRepository<Faq, Integer> {

    // Lista tutte le FAQ ordinate per 'ordine' crescente
    List<Faq> findAllByOrderByOrdineAsc();

    // Lista le FAQ filtrate per categoria e ordinate per 'ordine'
    List<Faq> findAllByCategoriaOrderByOrdineAsc(CategoriaFaq categoria);
}
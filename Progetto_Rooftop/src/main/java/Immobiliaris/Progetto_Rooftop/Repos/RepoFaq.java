package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing FAQs.
 * Exposes derived query methods leveraging Spring Data JPA conventions.
 */
@Repository
public interface RepoFaq extends JpaRepository<Faq, Integer> {

    /**
     * Returns all FAQs ordered by the 'ordine' field ascending.
     */
    List<Faq> findAllByOrderByOrdineAsc();

    /**
     * Returns FAQs filtered by the given category, ordered by 'ordine' ascending.
     * @param categoria category filter
     */
    List<Faq> findAllByCategoriaOrderByOrdineAsc(CategoriaFaq categoria);
}
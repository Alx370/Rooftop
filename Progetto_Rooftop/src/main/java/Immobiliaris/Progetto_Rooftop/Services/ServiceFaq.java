package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface defining business operations for FAQ management.
 * Implementations should apply validation and delegate persistence to repositories.
 */
@Service
public interface ServiceFaq {

    /**
     * Creates a new FAQ entry.
     * @param faq payload containing category, question, answer and order
     * @return the persisted FAQ entity
     */
    Faq create(Faq faq);

    /**
     * Returns all FAQs ordered by the 'ordine' field ascending.
     * @return ordered list of FAQs
     */
    List<Faq> getAllOrdered();

    /**
     * Retrieves a FAQ by its ID.
     * @param id identifier of the FAQ
     * @return the FAQ entity
     */
    Faq getById(Integer id);

    /**
     * Retrieves FAQs by category ordered by 'ordine'.
     * @param categoria enum category filter
     * @return list of FAQs matching the category
     */
    List<Faq> getByCategoria(CategoriaFaq categoria);

    /**
     * Updates an existing FAQ with the given payload.
     * @param id identifier of the FAQ to update
     * @param updated payload containing new values
     * @return the updated FAQ entity
     */
    Faq update(Integer id, Faq updated);

    /**
     * Deletes a FAQ by ID.
     * @param id identifier of the FAQ to delete
     */
    void delete(Integer id);

    /**
     * Creates a new FAQ from a contact request (RichiestaContatto).
     * This method is used by admins to quickly transform a contact request into an FAQ.
     * @param richiestaId identifier of the RichiestaContatto to convert
     * @param risposta the answer to the question (provided by admin)
     * @param categoria the FAQ category (provided by admin)
     * @return the created FAQ entity
     */
    Faq createFromRequest(Integer richiestaId, String risposta, CategoriaFaq categoria);
}
package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import Immobiliaris.Progetto_Rooftop.Repos.RepoFaq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service layer implementation for managing FAQs.
 * - Applies basic validation rules on input payloads
 * - Delegates persistence to RepoFaq
 * - Uses @Transactional to ensure atomic operations
 */
@Service
@Transactional
public class ServiceFaqImpl implements ServiceFaq {

    /**
     * Repository backing the FAQ CRUD operations.
     */
    @Autowired
    private RepoFaq faqRepo;

    /**
     * Constructor for dependency injection.
     * @param faqRepo repository for FAQ persistence operations
     */
    public ServiceFaqImpl(RepoFaq faqRepo) {
        this.faqRepo = faqRepo;
    }

    /**
     * Creates a new FAQ after validating required fields.
     * - categoria must be provided
     * - domanda/risposta must be non-empty
     * - domanda/risposta are trimmed to avoid leading/trailing spaces
     * @param faq FAQ payload to create
     * @return the persisted FAQ entity
     */
    @Override
    public Faq create(Faq faq) {
        if (faq.getCategoria() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria e richiesta");
        }
        if (faq.getDomanda() == null || faq.getDomanda().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Domanda e richiesta");
        }
        if (faq.getRisposta() == null || faq.getRisposta().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Risposta e richiesta");
        }

        // Normalize text fields
        faq.setDomanda(faq.getDomanda().trim());
        faq.setRisposta(faq.getRisposta().trim());

        return faqRepo.save(faq);
    }

    /**
     * Retrieves all FAQs ordered by the 'ordine' field ascending.
     * @return list of FAQs ordered by 'ordine'
     */
    @Override
    @Transactional(readOnly = true)
    public List<Faq> getAllOrdered() {
        return faqRepo.findAllByOrderByOrdineAsc();
    }

    /**
     * Retrieves a FAQ by its ID or throws NOT_FOUND.
     * @param id FAQ identifier
     * @return the FAQ entity
     */
    @Override
    @Transactional(readOnly = true)
    public Faq getById(Integer id) {
        return faqRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ non trovata"));
    }

    /**
     * Retrieves FAQs by category ordered by 'ordine'.
     * Validates that the category is provided.
     * @param categoria enum category
     * @return list of FAQs matching the given category
     */
    @Override
    @Transactional(readOnly = true)
    public List<Faq> getByCategoria(CategoriaFaq categoria) {
        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria e richiesta");
        }
        return faqRepo.findAllByCategoriaOrderByOrdineAsc(categoria);
    }

    /**
     * Updates an existing FAQ.
     * - Only applies non-null fields for domanda/risposta/categoria
     * - Trims domanda/risposta if provided and ensures they are not empty
     * - Always updates 'ordine' since it's a primitive int in the model
     * @param id FAQ identifier
     * @param updated payload containing the new values
     * @return the updated FAQ entity
     */
    @Override
    public Faq update(Integer id, Faq updated) {
        Faq existing = getById(id);

        if (updated.getCategoria() != null) {
            existing.setCategoria(updated.getCategoria());
        }
        if (updated.getDomanda() != null) {
            String d = updated.getDomanda().trim();
            if (d.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Domanda e richiesta");
            }
            existing.setDomanda(d);
        }
        if (updated.getRisposta() != null) {
            String r = updated.getRisposta().trim();
            if (r.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Risposta e richiesta");
            }
            existing.setRisposta(r);
        }
        // Update order (primitive int). If you want partial updates, consider Integer.
        existing.setOrdine(updated.getOrdine());

        return faqRepo.save(existing);
    }

    /**
     * Deletes a FAQ by id or throws NOT_FOUND if it does not exist.
     * @param id FAQ identifier
     */
    @Override
    public void delete(Integer id) {
        if (!faqRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ non trovata");
        }
        faqRepo.deleteById(id);
    }

    /**
     * Exposed getter for testing or configuration.
     */
    public RepoFaq getFaqRepo() {
        return faqRepo;
    }

    /**
     * Exposed setter for testing or configuration.
     */
    public void setFaqRepo(RepoFaq faqRepo) {
        this.faqRepo = faqRepo;
    }
}
package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import Immobiliaris.Progetto_Rooftop.Repos.RepoFaq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ServiceFaqImpl implements ServiceFaq {

    @Autowired
    private RepoFaq faqRepo;

    public ServiceFaqImpl(RepoFaq faqRepo) {
        this.faqRepo = faqRepo;
    }

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

        faq.setDomanda(faq.getDomanda().trim());
        faq.setRisposta(faq.getRisposta().trim());

        return faqRepo.save(faq);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faq> getAllOrdered() {
        return faqRepo.findAllByOrderByOrdineAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Faq getById(Integer id) {
        return faqRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ non trovata"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faq> getByCategoria(CategoriaFaq categoria) {
        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria e richiesta");
        }
        return faqRepo.findAllByCategoriaOrderByOrdineAsc(categoria);
    }

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
        // Aggiorna l'ordine sempre (primitivo int)
        existing.setOrdine(updated.getOrdine());

        return faqRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!faqRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ non trovata");
        }
        faqRepo.deleteById(id);
    }

    public RepoFaq getFaqRepo() {
        return faqRepo;
    }

    public void setFaqRepo(RepoFaq faqRepo) {
        this.faqRepo = faqRepo;
    }
}
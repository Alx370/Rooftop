package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceFaq {

    Faq create(Faq faq);

    List<Faq> getAllOrdered();

    Faq getById(Integer id);

    List<Faq> getByCategoria(CategoriaFaq categoria);

    Faq update(Integer id, Faq updated);

    void delete(Integer id);
}
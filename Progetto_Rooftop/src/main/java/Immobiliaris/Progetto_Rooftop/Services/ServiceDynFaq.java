package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.DynFaq;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceDynFaq {

    DynFaq create(DynFaq faq);

    List<DynFaq> getAllOrdered();

    DynFaq getById(Integer id);
    List<DynFaq> getByCategoria(CategoriaFaq categoria);

    DynFaq update(Integer id, DynFaq updated);

    void delete(Integer id);
}

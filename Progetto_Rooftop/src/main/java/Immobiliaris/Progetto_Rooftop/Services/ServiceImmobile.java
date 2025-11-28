package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Valutazione;

@Service
public interface ServiceImmobile {

    Immobile create(Immobile immobile);

    List<Immobile> getAll();

    Immobile getById(int id);

    Immobile update(int id, Immobile updated);

    void delete(int id);

    Valutazione stimaAutomatica(Integer idImmobile, BigDecimal prezzoMqZona);

    Valutazione stimaAutomaticaDaOMI(Integer idImmobile);
}

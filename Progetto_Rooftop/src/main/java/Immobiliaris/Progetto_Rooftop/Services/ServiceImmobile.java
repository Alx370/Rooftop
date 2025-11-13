package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;

@Service
public interface ServiceImmobile {

    Immobile create(Immobile immobile);

    List<Immobile> getAll();

    Immobile getById(int id);

    Immobile update(int id, Immobile updated);

    void delete(int id);    
}

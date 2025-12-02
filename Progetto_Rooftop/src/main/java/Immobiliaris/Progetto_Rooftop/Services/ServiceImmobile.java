package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;

@Service
public interface ServiceImmobile {

    /**
     * Crea un nuovo immobile.
     * @param immobile dati dell'immobile da creare
     * @return immobile creato
     */
    Immobile create(Immobile immobile);

    /**
     * Restituisce tutti gli immobili.
     * @return lista di immobili
     */
    List<Immobile> getAll();

    /**
     * Restituisce un immobile per id.
     * @param id identificativo immobile
     * @return immobile trovato
     */
    Immobile getById(int id);

    /**
     * Aggiorna un immobile esistente.
     * @param id identificativo immobile
     * @param updated campi da aggiornare
     * @return immobile aggiornato
     */
    Immobile update(int id, Immobile updated);

    /**
     * Elimina un immobile per id.
     * @param id identificativo immobile
     */
    void delete(int id);
}

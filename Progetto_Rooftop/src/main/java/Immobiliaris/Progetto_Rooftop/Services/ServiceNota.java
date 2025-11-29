package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Enum.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Model.Nota;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaccia di servizio per la gestione delle Note interne.
 * Applica validazioni e delega la persistenza al repository.
 */
@Service
public interface ServiceNota {

    /** Crea una nuova nota applicando validazioni e valori di default. */
    Nota create(Nota nota);

    /** Restituisce tutte le note ordinate per data di creazione decrescente. */
    List<Nota> getAllOrdered();

    /** Restituisce una nota per id oppure NOT_FOUND se non esiste. */
    Nota getById(Integer id);

    /** Restituisce le note filtrate per id agente, ordinate per data di creazione decrescente. */
    List<Nota> getByAgente(Integer idAgente);

    /** Restituisce le note filtrate per id immobile, ordinate per data di creazione decrescente. */
    List<Nota> getByImmobile(Integer idImmobile);

    /** Restituisce le note filtrate per id immobile e visibilità, ordinate per data di creazione decrescente. */
    List<Nota> getByImmobileAndVisibilita(Integer idImmobile, VisibilitaNota visibilita);

    /** Aggiorna una nota esistente con il payload fornito. */
    Nota update(Integer id, Nota updated);

    /** Elimina una nota per id oppure NOT_FOUND se non esiste. */
    void delete(Integer id);
}
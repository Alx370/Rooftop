package Immobiliaris.Progetto_Rooftop.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaAbitazione;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Enum.Tipologia;
import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Valutazione;

@Service
/**
 * Contratto del servizio per la gestione degli immobili.
 * Fornisce operazioni CRUD e metodi di valutazione automatica/manuale.
 */
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

    /**
     * Calcola una valutazione automatica usando il prezzo al mq della zona.
     * @param idImmobile id immobile
     * @param prezzoMqZona prezzo al metro quadro della zona
     * @return valutazione stimata
     */
    Valutazione stimaAutomatica(Integer idImmobile, BigDecimal prezzoMqZona);

    /**
     * Calcola una valutazione automatica basata sui valori OMI.
     * @param idImmobile id immobile
     * @return valutazione stimata
     */
    Valutazione stimaAutomaticaDaOMI(Integer idImmobile);

    /**
     * Crea una valutazione manuale opzionalmente associata a un valutatore.
     * @param idImmobile id immobile
     * @param idValutatore id valutatore (opzionale)
     * @param prezzoMqZona prezzo al mq della zona
     * @return valutazione salvata
     */
    Valutazione creaValutazioneManuale(Integer idImmobile, Integer idValutatore, BigDecimal prezzoMqZona);

    /**
     * Calcola la valutazione automatica partendo da indirizzo e caratteristiche.
     * @param provincia sigla provincia (es. MI)
     * @param citta città
     * @param indirizzo via/piazza
     * @param civico numero civico (opzionale)
     * @param metriQuadri superficie in mq
     * @param tipologia tipologia immobile
     * @param categoria categoria abitativa (opzionale)
     * @param statoImmobile stato/condizioni dell'immobile
     * @param piano piano (stringa libera)
     * @param bagni numero di bagni
     * @param caratteristiche caratteristiche aggiuntive
     * @return valutazione stimata
     */
    Valutazione stimaAutomaticaDaIndirizzo(
            String provincia,
            String citta,
            String indirizzo,
            String civico,
            BigDecimal metriQuadri,
            Tipologia tipologia,
            CategoriaAbitazione categoria,
            StatoImmobile statoImmobile,
            String piano,
            Integer bagni,
            CaratteristicheImmobile caratteristiche
    );
}

package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.ValutazioneZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service per gestione valutazioni al mq per zona.
 * Espone operazioni di lettura, creazione/aggiornamento e cancellazione.
 */
@Service
public interface ServiceValutazioneZona {

    /** Restituisce tutte le valutazioni zona. */
    List<ValutazioneZona> getAll();

    /** Restituisce la valutazione per una specifica zona. */
    ValutazioneZona getByZona(ZonaProvinciaTorino zona);

    /** Crea o aggiorna (per zona) i valori di vendita/affitto. */
    ValutazioneZona upsert(ValutazioneZona payload);

    /** Aggiorna una valutazione esistente per id. */
    ValutazioneZona update(Long id, ValutazioneZona updated);

    /** Elimina una valutazione per id. */
    void delete(Long id);
}
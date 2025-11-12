package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.ValoriZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service per gestione valutazioni al mq per zona.
 * Espone operazioni di lettura, creazione/aggiornamento e cancellazione.
 */
@Service
public interface ServiceValoriZona {

    /** Restituisce tutte le valutazioni zona. */
    List<ValoriZona> getAll();

    /** Restituisce la valutazione per una specifica zona. */
    ValoriZona getByZona(ZonaProvinciaTorino zona);

    /** Restituisce tutte le valutazioni per una provincia. */
    java.util.List<ValoriZona> getAllByProvincia(String provincia);

    /** Restituisce la valutazione per provincia e zona. */
    ValoriZona getByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona);

    /** Crea o aggiorna (per zona) i valori di vendita/affitto. */
    ValoriZona upsert(ValoriZona payload);

    /** Aggiorna una valutazione esistente per id. */
    ValoriZona update(Long id, ValoriZona updated);

    /** Elimina una valutazione per id. */
    void delete(Long id);

    /**
     * Calcola l'affitto totale: valoreMqAffitto(zona[,provincia]) * mq.
     * Se ammobiliato=true viene applicata una maggiorazione fissa (8%).
     */
    BigDecimal calcolaAffitto(String provincia,
                              ZonaProvinciaTorino zona,
                              BigDecimal metriQuadrati,
                              boolean ammobiliato);
}
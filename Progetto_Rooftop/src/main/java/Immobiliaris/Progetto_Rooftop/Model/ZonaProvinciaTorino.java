package Immobiliaris.Progetto_Rooftop.Model;

/**
 * Enum che rappresenta le zone della provincia di Torino.
 * Usare questo enum come chiave/identificatore per le valutazioni
 * (valore al mq vendita / valore al mq affitto) memorizzate in DB.
 *
 * Nota: i valori numerici sono meglio gestiti in una tabella DB separata
 * (vedi ValutazioneZona) così da poterli aggiornare senza ricompilare.
 */
public enum ZonaProvinciaTorino {

    TORINO_CENTRO("Torino - Centro"),
    TORINO_NORD("Torino - Nord"),
    TORINO_SUD("Torino - Sud"),
    TORINO_OVEST("Torino - Ovest"),
    TORINO_EST("Torino - Est"),
    CINTURA_NORD("Cintura - Nord"),
    CINTURA_SUD("Cintura - Sud"),
    PROVINCIA_NORD("Provincia - Nord"),
    PROVINCIA_SUD("Provincia - Sud");

    private final String displayName;

    ZonaProvinciaTorino(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

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

    CENTRO_STORICO("Centro Storico"),
    CROCETTA("Crocetta"),
    SAN_SALVARIO("San Salvario"),
    CIT_TURIN("Cit Turin"),
    VANCHIGLIA("Vanchiglia"),
    AURORA("Aurora"),
    BORGO_SAN_PAOLO("Borgo San Paolo"),
    CENISIA("Cenisia"),
    SANTA_RITA("Santa Rita"),
    MIRAFIORI_NORD("Mirafiori Nord"),
    MIRAFIORI_SUD("Mirafiori Sud"),
    POZZO_STRADA("Pozzo Strada"),
    PARELLA("Parella"),
    BORGO_VITTORIA("Borgo Vittoria"),
    BARRIERA_DI_MILANO("Barriera di Milano"),
    MADONNA_DI_CAMPAGNA("Madonna di Campagna"),
    REGIO_PARCO("Regio Parco"),
    LINGOTTO("Lingotto"),
    NIZZA_MILLEFONTI("Nizza Millefonti"),
    SAN_DONATO("San Donato"),
    VALLETTE("Vallette"),
    LUCENTO("Lucento"),
    FALCHERA("Falchera"),
    MADONNA_DEL_PILONE("Madonna del Pilone"),
    COLLINA_TORINESE_CRIMEA_CAVORETTO("Collina Torinese (zona Crimea, Cavoretto)"),
    GRAN_MADRE("Gran Madre"),
    BORGO_PO("Borgo Po"),
    SAN_SECONDO("San Secondo"),
    LINGOTTO_SUD("Lingotto Sud"),
    CAMPIDOGLIO("Campidoglio"),
    BORGATA_LESNA("Borgata Lesna"),
    MIRAFIORI_DROSSO("Mirafiori – Drosso"),
    REBAUDENGO("Rebaudengo"),
    BARCA_BERTOLLA("Barca-Bertolla"),
    PIETRA_ALTA("Pietra Alta");

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

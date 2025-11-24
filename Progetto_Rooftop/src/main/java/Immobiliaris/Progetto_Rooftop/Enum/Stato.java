package Immobiliaris.Progetto_Rooftop.Enum;

import Immobiliaris.Progetto_Rooftop.Model.Utente;

/**
 * Stato applicato a utenti e proprietari.
 * Mappato come stringa nel database tramite `@Enumerated(EnumType.STRING)`
 * e usato dalle entità {@link Utente} e {@link Proprietario}.
 */
public enum Stato {
    /** Entità attiva e abilitata alle operazioni. */
    ATTIVO,
    /** Entità disabilitata (non più attiva). */
    DISABILITATO
}



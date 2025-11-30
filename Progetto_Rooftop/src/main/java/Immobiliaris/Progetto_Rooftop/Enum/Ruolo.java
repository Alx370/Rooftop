package Immobiliaris.Progetto_Rooftop.Enum;

import Immobiliaris.Progetto_Rooftop.Model.Utente;

/**
 * Ruoli utente disponibili nel sistema.
 * Mappati come stringa nel database tramite `@Enumerated(EnumType.STRING)`
 * e usati dall'entità {@link Utente}.
 */
public enum Ruolo {
    /** Può gestire utenti, dati e configurazioni di sistema. */
    AMMINISTRATORE,
    /** Agente immobiliare operativo. */
    AGENTE,
    /** Utente dedicato alle valutazioni degli immobili. */
    VALUTATORE,
    /** Proprietario di immobili che utilizza il sistema per gestirli. */
    PROPRIETARIO
}

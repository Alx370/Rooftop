package Immobiliaris.Progetto_Rooftop.Enum;

import Immobiliaris.Progetto_Rooftop.Model.Nota;

/**
 * Definisce la visibilità delle note interne.
 * Usato dall'entità {@link Nota} e salvato come stringa nel database
 * tramite `@Enumerated(EnumType.STRING)`.
 */
public enum VisibilitaNota {
    /** La nota è visibile a tutto il team. */
    TEAM,
    /** La nota è visibile solo all'autore. */
    PRIVATA
}
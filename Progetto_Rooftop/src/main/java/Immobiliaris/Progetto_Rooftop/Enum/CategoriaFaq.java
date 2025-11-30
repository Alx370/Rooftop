package Immobiliaris.Progetto_Rooftop.Enum;

/**
 * Enum representing available categories for FAQs.
 * Values must match the database ENUM for the `categoria` column in `faq` table.
 */
public enum CategoriaFaq {
    /** Questions related to selling properties. */
    VENDITA,
    /** Questions related to buying properties. */
    ACQUISTO,
    /** Questions about property valuation and estimations. */
    VALUTAZIONE,
    /** Questions regarding contracts and legal agreements. */
    CONTRATTI,
    /** Questions about documents and paperwork. */
    DOCUMENTI,
    /** Questions related to mortgages and financing. */
    MUTUI,
    /** Questions about services offered. */
    SERVIZI,
    /** General questions not fitting a specific category. */
    GENERALE
}
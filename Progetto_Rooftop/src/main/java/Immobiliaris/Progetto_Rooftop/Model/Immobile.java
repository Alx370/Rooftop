package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import Immobiliaris.Progetto_Rooftop.Enum.StatoAnnuncio;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Enum.Tipologia;
import Immobiliaris.Progetto_Rooftop.Enum.CategoriaAbitazione;

@Entity
@Table(name = "immobili")
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_immobile")
    private Integer id_immobile;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Utente proprietario;
    // Foreign key to Utente (proprietario) - required field linking property to owner.

    @Column(name = "id_agente")
    private Integer id_agente;
    // Foreign key to agente - optional field for assigned agent.

    @Column(nullable = false, length = 200)
    private String titolo;
    // Property listing title - required field, max 200 characters.

    @Column(columnDefinition = "TEXT")
    private String descrizione;
    // Detailed property description - optional field, TEXT type allows longer content.

    @Column(nullable = false, length = 255)
    private String indirizzo;
    // Street address - required field, max 255 characters.

    @Column(length = 20)
    private String civico;
    // Street number - optional field, max 20 characters.

    @Column(nullable = false, length = 100)
    private String citta;
    // City name - required field, max 100 characters.

    @Column(nullable = false, length = 2)
    private String provincia;
    // Province code (e.g., "MI", "RM") - required field, 2 characters.

    @Column(nullable = false, length = 10)
    private String cap;
    // Postal code - required field, max 10 characters.

    @Column(length = 100)
    private String quartiere;
    // Neighborhood/district - optional field, max 100 characters.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipologia tipologia;
    // Property type (APPARTAMENTO, VILLA, etc.) - required field stored as string.

    @Column(name = "metri_quadri", precision = 7, scale = 2, nullable = false)
    private BigDecimal metri_quadri;
    // Square meters - required field, precision 7 digits with 2 decimal places.

    @Column(length = 15)
    private String piano;
    // Floor level - optional field, max 15 characters (allows "PT", "1", "Attico", etc.).

    @Column(name = "anno_costruzione")
    private Integer anno_costruzione;
    // Year of construction - optional field.

    @Column(name = "prezzo_richiesto", precision = 10, scale = 2, nullable = false)
    private BigDecimal prezzo_richiesto;
    // Asking price - required field, precision 10 digits with 2 decimal places.

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_immobile", length = 20)
    private StatoImmobile stato_immobile;
    // Property condition (OTTIMO, BUONO, etc.) - stored as string.

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_annuncio", length = 20)
    private StatoAnnuncio stato_annuncio;
    // Listing status (VALUTAZIONE, PUBBLICATO, etc.) - stored as string.

    @Column(name = "creato_il", columnDefinition = "DATETIME")
    private LocalDateTime creato_il;
    // Creation timestamp - automatically set when property is created

    public Integer getId_immobile() {
        return id_immobile;
    }

    public void setId_immobile(Integer id_immobile) {
        this.id_immobile = id_immobile;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public Integer getId_agente() {
        return id_agente;
    }

    public void setId_agente(Integer id_agente) {
        this.id_agente = id_agente;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getQuartiere() {
        return quartiere;
    }

    public void setQuartiere(String quartiere) {
        this.quartiere = quartiere;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public BigDecimal getMetri_quadri() {
        return metri_quadri;
    }

    public void setMetri_quadri(BigDecimal metri_quadri) {
        this.metri_quadri = metri_quadri;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public Integer getAnno_costruzione() {
        return anno_costruzione;
    }

    public void setAnno_costruzione(Integer anno_costruzione) {
        this.anno_costruzione = anno_costruzione;
    }

    public Integer getBagni() {
        return bagni;
    }

    public void setBagni(Integer bagni) {
        this.bagni = bagni;
    }

    public CategoriaAbitazione getCategoria_abitazione() {
        return categoria_abitazione;
    }

    public void setCategoria_abitazione(CategoriaAbitazione categoria_abitazione) {
        this.categoria_abitazione = categoria_abitazione;
    }

    public BigDecimal getPrezzo_richiesto() {
        return prezzo_richiesto;
    }

    public void setPrezzo_richiesto(BigDecimal prezzo_richiesto) {
        this.prezzo_richiesto = prezzo_richiesto;
    }

    public StatoImmobile getStato_immobile() {
        return stato_immobile;
    }

    public void setStato_immobile(StatoImmobile stato_immobile) {
        this.stato_immobile = stato_immobile;
    }

    public StatoAnnuncio getStato_annuncio() {
        return stato_annuncio;
    }

    public void setStato_annuncio(StatoAnnuncio stato_annuncio) {
        this.stato_annuncio = stato_annuncio;
    }

    public LocalDateTime getCreato_il() {
        return creato_il;
    }

    public void setCreato_il(LocalDateTime creato_il) {
        this.creato_il = creato_il;
    }
}

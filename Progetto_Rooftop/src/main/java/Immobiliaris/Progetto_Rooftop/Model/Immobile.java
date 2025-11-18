package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "id_agente")
    private Integer id_agente;

    @Column(nullable = false, length = 200)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @Column(nullable = false, length = 255)
    private String indirizzo;

    @Column(length = 20)
    private String civico;

    @Column(nullable = false, length = 100)
    private String citta;

    @Column(nullable = false, length = 2)
    private String provincia;

    @Column(nullable = false, length = 10)
    private String cap;

    @Column(length = 100)
    private String quartiere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipologia tipologia;

    @Column(name = "metri_quadri", precision = 7, scale = 2, nullable = false)
    private BigDecimal metri_quadri;

    @Column(name = "locali")
    private Integer locali;

    @Column(name = "bagni")
    private Integer bagni;

    @Column(length = 15)
    private String piano;

    @Column(name = "anno_costruzione")
    private Integer anno_costruzione;

    @Column(name = "prezzo_richiesto", precision = 10, scale = 2, nullable = false)
    private BigDecimal prezzo_richiesto;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_immobile", length = 20)
    private StatoImmobile stato_immobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_annuncio", length = 20)
    private StatoAnnuncio stato_annuncio;

    @Column(name = "creato_il", columnDefinition = "DATETIME")
    private LocalDateTime creato_il;

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

    public Integer getLocali() {
        return locali;
    }

    public void setLocali(Integer locali) {
        this.locali = locali;
    }

    public Integer getBagni() {
        return bagni;
    }

    public void setBagni(Integer bagni) {
        this.bagni = bagni;
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

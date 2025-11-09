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
    private Integer idImmobile;

    @Column(name = "id_proprietario", nullable = false)
    private Integer idProprietario;

    @Column(name = "id_agente")
    private Integer idAgente;

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
    private BigDecimal metriQuadri;

    @Column(length = 15)
    private String piano;

    @Column(name = "anno_costruzione")
    private Integer annoCostruzione;

    // Prezzo e stati
    @Column(name = "prezzo_richiesto", precision = 10, scale = 2, nullable = false)
    private BigDecimal prezzoRichiesto;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_immobile", length = 20)
    private StatoImmobile statoImmobile = StatoImmobile.BUONO;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_annuncio", length = 20)
    private StatoAnnuncio statoAnnuncio = StatoAnnuncio.VALUTAZIONE;

    @Column(name = "creato_il", columnDefinition = "DATETIME")
    private LocalDateTime creatoIl = LocalDateTime.now();

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Integer getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Integer idProprietario) {
        this.idProprietario = idProprietario;
    }

    public Integer getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Integer idAgente) {
        this.idAgente = idAgente;
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

    public BigDecimal getMetriQuadri() {
        return metriQuadri;
    }

    public void setMetriQuadri(BigDecimal metriQuadri) {
        this.metriQuadri = metriQuadri;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public Integer getAnnoCostruzione() {
        return annoCostruzione;
    }

    public void setAnnoCostruzione(Integer annoCostruzione) {
        this.annoCostruzione = annoCostruzione;
    }

    public BigDecimal getPrezzoRichiesto() {
        return prezzoRichiesto;
    }

    public void setPrezzoRichiesto(BigDecimal prezzoRichiesto) {
        this.prezzoRichiesto = prezzoRichiesto;
    }

    public StatoImmobile getStatoImmobile() {
        return statoImmobile;
    }

    public void setStatoImmobile(StatoImmobile statoImmobile) {
        this.statoImmobile = statoImmobile;
    }

    public StatoAnnuncio getStatoAnnuncio() {
        return statoAnnuncio;
    }

    public void setStatoAnnuncio(StatoAnnuncio statoAnnuncio) {
        this.statoAnnuncio = statoAnnuncio;
    }

    public LocalDateTime getCreatoIl() {
        return creatoIl;
    }

    public void setCreatoIl(LocalDateTime creatoIl) {
        this.creatoIl = creatoIl;
    }

}
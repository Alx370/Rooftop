package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "valutazioni")
public class Valutazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valutazione")
    private Integer idValutazione;

    @ManyToOne
    @JoinColumn(name = "id_immobile", nullable = false)
    private Immobile immobile;

    @ManyToOne
    @JoinColumn(name = "id_valutatore")
    private Utente valutatore;

    @Column(name = "valore_stimato", precision = 10, scale = 2, nullable = false)
    private BigDecimal valoreStimato;

    @Column(name = "valore_min", precision = 10, scale = 2)
    private BigDecimal valoreMin;

    @Column(name = "valore_max", precision = 10, scale = 2)
    private BigDecimal valoreMax;

    @Enumerated(EnumType.STRING)
    private StatoValutazione stato = StatoValutazione.IN_LAVORAZIONE;

    @Enumerated(EnumType.STRING)
    private MetodoValutazione metodo = MetodoValutazione.AUTOMATICO;

    @Column(name = "data_valutazione")
    private LocalDateTime dataValutazione = LocalDateTime.now();

    @Column(name = "data_scadenza")
    private LocalDateTime dataScadenza;

    @Column(name = "note")
    private String note;

    public Integer getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(Integer idValutazione) {
        this.idValutazione = idValutazione;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Utente getValutatore() {
        return valutatore;
    }

    public void setValutatore(Utente valutatore) {
        this.valutatore = valutatore;
    }

    public BigDecimal getValoreStimato() {
        return valoreStimato;
    }

    public void setValoreStimato(BigDecimal valoreStimato) {
        this.valoreStimato = valoreStimato;
    }

    public BigDecimal getValoreMin() {
        return valoreMin;
    }

    public void setValoreMin(BigDecimal valoreMin) {
        this.valoreMin = valoreMin;
    }

    public BigDecimal getValoreMax() {
        return valoreMax;
    }

    public void setValoreMax(BigDecimal valoreMax) {
        this.valoreMax = valoreMax;
    }

    public StatoValutazione getStato() {
        return stato;
    }

    public void setStato(StatoValutazione stato) {
        this.stato = stato;
    }

    public MetodoValutazione getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoValutazione metodo) {
        this.metodo = metodo;
    }

    public LocalDateTime getDataValutazione() {
        return dataValutazione;
    }

    public void setDataValutazione(LocalDateTime dataValutazione) {
        this.dataValutazione = dataValutazione;
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
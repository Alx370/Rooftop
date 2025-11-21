package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "valutazioni")
public class Valutazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valutazione")
    private Integer id_valutazione;

    @ManyToOne
    @JoinColumn(name = "id_immobile", nullable = false)
    private Immobile immobile;
    // Foreign key to immobile - required field linking valuation to property.

    @ManyToOne
    @JoinColumn(name = "id_valutatore")
    private Utente valutatore;
    // Foreign key to valutatore - optional field for assigned evaluator.

    @Column(name = "valore_stimato", precision = 10, scale = 2, nullable = false)
    private BigDecimal valore_stimato;
    // Estimated value - required field, precision 10 digits with 2 decimal places.

    @Column(name = "valore_min", precision = 10, scale = 2)
    private BigDecimal valore_min;
    // Minimum estimated value - optional field for value range.

    @Column(name = "valore_max", precision = 10, scale = 2)
    private BigDecimal valore_max;
    // Maximum estimated value - optional field for value range.

    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private StatoValutazione stato;
    // Valuation status (IN_LAVORAZIONE, COMPLETATA, etc.) - stored as string.

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo")
    private MetodoValutazione metodo;
    // Valuation method (AUTOMATICO, MANUALE, etc.) - stored as string.

    @Column(name = "data_valutazione")
    private LocalDateTime data_valutazione;
    // Valuation date - timestamp when evaluation was performed.

    @Column(name = "data_scadenza")
    private LocalDateTime data_scadenza;
    // Expiration date - optional field for valuation validity period.

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
    // Additional notes - optional field, TEXT type allows longer content.

    public Integer getId_valutazione() {
        return id_valutazione;
    }

    public void setId_valutazione(Integer id_valutazione) {
        this.id_valutazione = id_valutazione;
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

    public BigDecimal getValore_stimato() {
        return valore_stimato;
    }

    public void setValore_stimato(BigDecimal valore_stimato) {
        this.valore_stimato = valore_stimato;
    }

    public BigDecimal getValore_min() {
        return valore_min;
    }

    public void setValore_min(BigDecimal valore_min) {
        this.valore_min = valore_min;
    }

    public BigDecimal getValore_max() {
        return valore_max;
    }

    public void setValore_max(BigDecimal valore_max) {
        this.valore_max = valore_max;
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

    public LocalDateTime getData_valutazione() {
        return data_valutazione;
    }

    public void setData_valutazione(LocalDateTime data_valutazione) {
        this.data_valutazione = data_valutazione;
    }

    public LocalDateTime getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDateTime data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
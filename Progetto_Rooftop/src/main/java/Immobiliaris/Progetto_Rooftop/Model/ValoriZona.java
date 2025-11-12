package Immobiliaris.Progetto_Rooftop.Model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entità che memorizza il valore medio al mq per la vendita e per l'affitto
 * per una specifica zona (identificata tramite l'enum `ZonaProvinciaTorino`).
 */
@Entity
@Table(name = "valutazione_zona")
public class ValoriZona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ZonaProvinciaTorinoConverter.class)
    @Column(nullable = false, length = 100)
    private ZonaProvinciaTorino zona;

    // provincia di riferimento (es. "Torino")
    @Column(name = "provincia", length = 200)
    private String provincia = "Torino";

    // valore medio vendita in euro al mq
    @Column(name = "valore_mq_vendita", precision = 12, scale = 2)
    private BigDecimal valoreMqVendita;

    // valore medio affitto in euro al mq
    @jakarta.persistence.Column(name = "valore_mq_affitto", precision = 12, scale = 2)
    private BigDecimal valoreMqAffitto;

    public ValoriZona() {
    }

    public ValoriZona(ZonaProvinciaTorino zona, BigDecimal valoreMqVendita, BigDecimal valoreMqAffitto) {
        this.zona = zona;
        this.valoreMqVendita = valoreMqVendita;
        this.valoreMqAffitto = valoreMqAffitto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonaProvinciaTorino getZona() {
        return zona;
    }

    public void setZona(ZonaProvinciaTorino zona) {
        this.zona = zona;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public BigDecimal getValoreMqVendita() {
        return valoreMqVendita;
    }

    public void setValoreMqVendita(BigDecimal valoreMqVendita) {
        this.valoreMqVendita = valoreMqVendita;
    }

    public BigDecimal getValoreMqAffitto() {
        return valoreMqAffitto;
    }

    public void setValoreMqAffitto(BigDecimal valoreMqAffitto) {
        this.valoreMqAffitto = valoreMqAffitto;
    }
}

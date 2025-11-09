package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "caratteristiche_immobile")
public class CaratteristicheImmobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caratteristica")
    private Integer idCaratteristica;

    @OneToOne
    @JoinColumn(name = "id_immobile", nullable = false)
    private Immobile immobile;

    // Dotazioni
    private Boolean ascensore = false;
    private Boolean parcheggio = false;
    @Column(name = "posti_auto")
    private Integer postiAuto = 0;
    private Boolean garage = false;
    private Boolean balcone = false;
    @Column(name = "balcone_mq", precision = 6, scale = 2)
    private BigDecimal balconeMq;
    private Boolean terrazzo = false;
    @Column(name = "terrazzo_mq", precision = 6, scale = 2)
    private BigDecimal terrazzoMq;
    private Boolean giardino = false;
    @Column(name = "giardino_mq", precision = 7, scale = 2)
    private BigDecimal giardinoMq;
    private Boolean cantina = false;
    private Boolean arredato = false;
    @Column(name = "aria_condizionata")
    private Boolean ariaCondizionata = false;
    private Boolean allarme = false;

    // Impianti
    @Enumerated(EnumType.STRING)
    private Riscaldamento riscaldamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe_energetica", length = 3)
    private ClasseEnergetica classeEnergetica;

    @Enumerated(EnumType.STRING)
    private Orientamento orientamento;

    // Costruttore
    public CaratteristicheImmobile() {}

    // Getters e Setters (alcuni esempi)
    public Integer getIdCaratteristica() { return idCaratteristica; }
    public void setIdCaratteristica(Integer idCaratteristica) { this.idCaratteristica = idCaratteristica; }

    public Immobile getImmobile() { return immobile; }
    public void setImmobile(Immobile immobile) { this.immobile = immobile; }

    public Boolean getAscensore() { return ascensore; }
    public void setAscensore(Boolean ascensore) { this.ascensore = ascensore; }

    public Boolean getParcheggio() { return parcheggio; }
    public void setParcheggio(Boolean parcheggio) { this.parcheggio = parcheggio; }

    public Riscaldamento getRiscaldamento() { return riscaldamento; }
    public void setRiscaldamento(Riscaldamento riscaldamento) { this.riscaldamento = riscaldamento; }

    // Aggiungi gli altri getter/setter come necessario
}
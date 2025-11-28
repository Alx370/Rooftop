package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;

import Immobiliaris.Progetto_Rooftop.Enum.ClasseEnergetica;
import Immobiliaris.Progetto_Rooftop.Enum.Orientamento;
import Immobiliaris.Progetto_Rooftop.Enum.Riscaldamento;

@Entity
@Table(name = "caratteristiche_immobile")
public class CaratteristicheImmobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caratteristica")
    private Integer id_caratteristica;

    @OneToOne
    @JoinColumn(name = "id_immobile", nullable = false)
    private Immobile immobile;
    // Foreign key to immobile - required field linking characteristics to property.

    @Column(name = "ascensore")
    private Boolean ascensore;
    // Elevator availability - optional boolean field.

    @Column(name = "parcheggio")
    private Boolean parcheggio;
    // Parking availability - optional boolean field.

    @Column(name = "posti_auto")
    private Integer posti_auto;
    // Number of parking spaces - optional field.

    @Column(name = "garage")
    private Boolean garage;
    // Garage availability - optional boolean field.

    @Column(name = "balcone")
    private Boolean balcone;
    // Balcony availability - optional boolean field.

    @Column(name = "balcone_mq", precision = 6, scale = 2)
    private BigDecimal balcone_mq;
    // Balcony square meters - optional field, precision 6 digits with 2 decimal places.

    @Column(name = "terrazzo")
    private Boolean terrazzo;
    // Terrace availability - optional boolean field.

    @Column(name = "terrazzo_mq", precision = 6, scale = 2)
    private BigDecimal terrazzo_mq;
    // Terrace square meters - optional field, precision 6 digits with 2 decimal places.

    @Column(name = "giardino")
    private Boolean giardino;
    // Garden availability - optional boolean field.

    @Column(name = "giardino_mq", precision = 7, scale = 2)
    private BigDecimal giardino_mq;
    // Garden square meters - optional field, precision 7 digits with 2 decimal places.

    @Column(name = "cantina")
    private Boolean cantina;
    // Cellar availability - optional boolean field.

    @Column(name = "arredato")
    private Boolean arredato;
    // Furnished - optional boolean field.

    @Column(name = "aria_condizionata")
    private Boolean aria_condizionata;
    // Air conditioning availability - optional boolean field.

    @Column(name = "allarme")
    private Boolean allarme;
    // Alarm system availability - optional boolean field.

    @Enumerated(EnumType.STRING)
    @Column(name = "riscaldamento")
    private Riscaldamento riscaldamento;
    // Heating type (AUTONOMO, CENTRALIZZATO, etc.) - stored as string.

    @Enumerated(EnumType.STRING)
    @Column(name = "classe_energetica", length = 3)
    private ClasseEnergetica classe_energetica;
    // Energy class (A, B, C, etc.) - stored as string, max 3 characters.

    @Enumerated(EnumType.STRING)
    @Column(name = "orientamento")
    private Orientamento orientamento;

    @Column(name = "indipendente")
    private Boolean indipendente;
    // Property orientation (NORD, SUD, EST, OVEST) - stored as string.

    public Integer getId_caratteristica() {
        return id_caratteristica;
    }

    public void setId_caratteristica(Integer id_caratteristica) {
        this.id_caratteristica = id_caratteristica;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Boolean getAscensore() {
        return ascensore;
    }

    public void setAscensore(Boolean ascensore) {
        this.ascensore = ascensore;
    }

    public Boolean getParcheggio() {
        return parcheggio;
    }

    public void setParcheggio(Boolean parcheggio) {
        this.parcheggio = parcheggio;
    }

    public Integer getPosti_auto() {
        return posti_auto;
    }

    public void setPosti_auto(Integer posti_auto) {
        this.posti_auto = posti_auto;
    }

    public Boolean getGarage() {
        return garage;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Boolean getBalcone() {
        return balcone;
    }

    public void setBalcone(Boolean balcone) {
        this.balcone = balcone;
    }

    public BigDecimal getBalcone_mq() {
        return balcone_mq;
    }

    public void setBalcone_mq(BigDecimal balcone_mq) {
        this.balcone_mq = balcone_mq;
    }

    public Boolean getTerrazzo() {
        return terrazzo;
    }

    public void setTerrazzo(Boolean terrazzo) {
        this.terrazzo = terrazzo;
    }

    public BigDecimal getTerrazzo_mq() {
        return terrazzo_mq;
    }

    public void setTerrazzo_mq(BigDecimal terrazzo_mq) {
        this.terrazzo_mq = terrazzo_mq;
    }

    public Boolean getGiardino() {
        return giardino;
    }

    public void setGiardino(Boolean giardino) {
        this.giardino = giardino;
    }

    public BigDecimal getGiardino_mq() {
        return giardino_mq;
    }

    public void setGiardino_mq(BigDecimal giardino_mq) {
        this.giardino_mq = giardino_mq;
    }

    public Boolean getCantina() {
        return cantina;
    }

    public void setCantina(Boolean cantina) {
        this.cantina = cantina;
    }

    public Boolean getArredato() {
        return arredato;
    }

    public void setArredato(Boolean arredato) {
        this.arredato = arredato;
    }

    public Boolean getAria_condizionata() {
        return aria_condizionata;
    }

    public void setAria_condizionata(Boolean aria_condizionata) {
        this.aria_condizionata = aria_condizionata;
    }

    public Boolean getAllarme() {
        return allarme;
    }

    public void setAllarme(Boolean allarme) {
        this.allarme = allarme;
    }

    public Riscaldamento getRiscaldamento() {
        return riscaldamento;
    }

    public void setRiscaldamento(Riscaldamento riscaldamento) {
        this.riscaldamento = riscaldamento;
    }

    public ClasseEnergetica getClasse_energetica() {
        return classe_energetica;
    }

    public void setClasse_energetica(ClasseEnergetica classe_energetica) {
        this.classe_energetica = classe_energetica;
    }

    public Orientamento getOrientamento() {
        return orientamento;
    }

    public void setOrientamento(Orientamento orientamento) {
        this.orientamento = orientamento;
    }

    public Boolean getIndipendente() {
        return indipendente;
    }

    public void setIndipendente(Boolean indipendente) {
        this.indipendente = indipendente;
    }
}

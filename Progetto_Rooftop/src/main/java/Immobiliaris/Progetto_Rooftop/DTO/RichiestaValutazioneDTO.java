package Immobiliaris.Progetto_Rooftop.DTO;

import java.math.BigDecimal;

import Immobiliaris.Progetto_Rooftop.Enum.ClasseEnergetica;
import Immobiliaris.Progetto_Rooftop.Enum.Orientamento;
import Immobiliaris.Progetto_Rooftop.Enum.Riscaldamento;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Enum.Tipologia;

/**
 * DTO per la richiesta di valutazione automatica.
 * Contiene tutti i dati dell'immobile e le caratteristiche necessarie per la stima.
 * 
 * Campi obbligatori: provincia, cap, indirizzo, metriQuadri
 * La città viene risolta automaticamente da Nominatim usando il CAP.
 */
public class RichiestaValutazioneDTO {

    // Dati obbligatori per trovare la zona OMI
    private String provincia;       // es. "TO" - serve per velocizzare query DB
    private String cap;             // es. "10151" - identifica la città
    private String indirizzo;       // es. "Via delle Pervinche"
    private String civico;          // es. "57" (opzionale)

    // Dati immobile
    private BigDecimal metriQuadri;
    private Tipologia tipologia;    // APPARTAMENTO, VILLA, MONOLOCALE, ATTICO, TERRENO, ALTRO
    private StatoImmobile statoImmobile; // NUOVO, BUONO, DA_RISTRUTTURARE
    private String piano;
    private Integer locali;
    private Integer bagni;
    private Integer annoCost;

    // Caratteristiche immobile
    private Boolean ascensore;
    private Boolean parcheggio;
    private Integer postiAuto;
    private Boolean garage;
    private BigDecimal balconeMq;
    private BigDecimal terrazzoMq;
    private BigDecimal giardinoMq;
    private Boolean cantina;
    private Boolean arredato;
    private Boolean ariaCondizionata;
    private Boolean allarme;
    private Riscaldamento riscaldamento;
    private ClasseEnergetica classeEnergetica;
    private Orientamento orientamento;

    // Getters e Setters
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getCivico() { return civico; }
    public void setCivico(String civico) { this.civico = civico; }

    public BigDecimal getMetriQuadri() { return metriQuadri; }
    public void setMetriQuadri(BigDecimal metriQuadri) { this.metriQuadri = metriQuadri; }

    public Tipologia getTipologia() { return tipologia; }
    public void setTipologia(Tipologia tipologia) { this.tipologia = tipologia; }

    public StatoImmobile getStatoImmobile() { return statoImmobile; }
    public void setStatoImmobile(StatoImmobile statoImmobile) { this.statoImmobile = statoImmobile; }

    public String getPiano() { return piano; }
    public void setPiano(String piano) { this.piano = piano; }

    public Integer getLocali() { return locali; }
    public void setLocali(Integer locali) { this.locali = locali; }

    public Integer getBagni() { return bagni; }
    public void setBagni(Integer bagni) { this.bagni = bagni; }

    public Integer getAnnoCost() { return annoCost; }
    public void setAnnoCost(Integer annoCost) { this.annoCost = annoCost; }

    public Boolean getAscensore() { return ascensore; }
    public void setAscensore(Boolean ascensore) { this.ascensore = ascensore; }

    public Boolean getParcheggio() { return parcheggio; }
    public void setParcheggio(Boolean parcheggio) { this.parcheggio = parcheggio; }

    public Integer getPostiAuto() { return postiAuto; }
    public void setPostiAuto(Integer postiAuto) { this.postiAuto = postiAuto; }

    public Boolean getGarage() { return garage; }
    public void setGarage(Boolean garage) { this.garage = garage; }

    public BigDecimal getBalconeMq() { return balconeMq; }
    public void setBalconeMq(BigDecimal balconeMq) { this.balconeMq = balconeMq; }

    public BigDecimal getTerrazzoMq() { return terrazzoMq; }
    public void setTerrazzoMq(BigDecimal terrazzoMq) { this.terrazzoMq = terrazzoMq; }

    public BigDecimal getGiardinoMq() { return giardinoMq; }
    public void setGiardinoMq(BigDecimal giardinoMq) { this.giardinoMq = giardinoMq; }

    public Boolean getCantina() { return cantina; }
    public void setCantina(Boolean cantina) { this.cantina = cantina; }

    public Boolean getArredato() { return arredato; }
    public void setArredato(Boolean arredato) { this.arredato = arredato; }

    public Boolean getAriaCondizionata() { return ariaCondizionata; }
    public void setAriaCondizionata(Boolean ariaCondizionata) { this.ariaCondizionata = ariaCondizionata; }

    public Boolean getAllarme() { return allarme; }
    public void setAllarme(Boolean allarme) { this.allarme = allarme; }

    public Riscaldamento getRiscaldamento() { return riscaldamento; }
    public void setRiscaldamento(Riscaldamento riscaldamento) { this.riscaldamento = riscaldamento; }

    public ClasseEnergetica getClasseEnergetica() { return classeEnergetica; }
    public void setClasseEnergetica(ClasseEnergetica classeEnergetica) { this.classeEnergetica = classeEnergetica; }

    public Orientamento getOrientamento() { return orientamento; }
    public void setOrientamento(Orientamento orientamento) { this.orientamento = orientamento; }
}

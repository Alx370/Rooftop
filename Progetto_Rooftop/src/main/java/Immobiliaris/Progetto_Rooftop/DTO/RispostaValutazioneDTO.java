package Immobiliaris.Progetto_Rooftop.DTO;

import java.math.BigDecimal;

/**
 * DTO per la risposta della valutazione automatica.
 * Contiene i valori stimati e le informazioni sulla zona OMI utilizzata.
 */
public class RispostaValutazioneDTO {

    private BigDecimal valoreStimato;
    private BigDecimal valoreMin;
    private BigDecimal valoreMax;
    private BigDecimal prezzoMqZona;
    private String zonaOmi;          // es. "B1"
    private String quartiereRilevato; // quartiere trovato da Nominatim
    private String metodo;           // "AUTOMATICO"
    private String note;

    public RispostaValutazioneDTO() {}

    public RispostaValutazioneDTO(BigDecimal valoreStimato, BigDecimal valoreMin, BigDecimal valoreMax,
                                   BigDecimal prezzoMqZona, String zonaOmi, String quartiereRilevato) {
        this.valoreStimato = valoreStimato;
        this.valoreMin = valoreMin;
        this.valoreMax = valoreMax;
        this.prezzoMqZona = prezzoMqZona;
        this.zonaOmi = zonaOmi;
        this.quartiereRilevato = quartiereRilevato;
        this.metodo = "AUTOMATICO";
    }

    // Getters e Setters
    public BigDecimal getValoreStimato() { return valoreStimato; }
    public void setValoreStimato(BigDecimal valoreStimato) { this.valoreStimato = valoreStimato; }

    public BigDecimal getValoreMin() { return valoreMin; }
    public void setValoreMin(BigDecimal valoreMin) { this.valoreMin = valoreMin; }

    public BigDecimal getValoreMax() { return valoreMax; }
    public void setValoreMax(BigDecimal valoreMax) { this.valoreMax = valoreMax; }

    public BigDecimal getPrezzoMqZona() { return prezzoMqZona; }
    public void setPrezzoMqZona(BigDecimal prezzoMqZona) { this.prezzoMqZona = prezzoMqZona; }

    public String getZonaOmi() { return zonaOmi; }
    public void setZonaOmi(String zonaOmi) { this.zonaOmi = zonaOmi; }

    public String getQuartiereRilevato() { return quartiereRilevato; }
    public void setQuartiereRilevato(String quartiereRilevato) { this.quartiereRilevato = quartiereRilevato; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}

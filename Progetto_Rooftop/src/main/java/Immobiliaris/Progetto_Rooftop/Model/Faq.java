package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faq")
    private int id_faq;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 20)
    private CategoriaFaq categoria;

    @Column(name = "domanda", nullable = false, columnDefinition = "TEXT")
    private String domanda;

    @Column(name = "risposta", nullable = false, columnDefinition = "TEXT")
    private String risposta;

    @Column(name = "ordine")
    private int ordine = 0;

    public Faq() {}

    public Faq(CategoriaFaq categoria, String domanda, String risposta, int ordine) {
        this.categoria = categoria;
        this.domanda = domanda;
        this.risposta = risposta;
        this.ordine = ordine;
    }

    public int getId_faq() {
        return id_faq;
    }

    public void setId_faq(int id_faq) {
        this.id_faq = id_faq;
    }

    public CategoriaFaq getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaFaq categoria) {
        this.categoria = categoria;
    }

    public String getDomanda() {
        return domanda;
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }
}
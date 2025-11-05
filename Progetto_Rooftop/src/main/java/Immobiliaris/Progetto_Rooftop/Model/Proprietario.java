package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "proprietari")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proprietario")
    private int id_proprietario;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "consenso_gdpr", nullable = false)
    private boolean consenso_gdpr;

    @Column(name = "consenso_marketing", nullable = false)
    private boolean consenso_marketing;

    @Column(name = "stato", nullable = false)
    private Stato stato;


    public int getId_proprietario() {
        return id_proprietario;
    }

    public void setId_proprietario(int id_proprietario) {
        this.id_proprietario = id_proprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isConsenso_gdpr() {
        return consenso_gdpr;
    }

    public void setConsenso_gdpr(boolean consenso_gdpr) {
        this.consenso_gdpr = consenso_gdpr;
    }

    public boolean isConsenso_marketing() {
        return consenso_marketing;
    }

    public void setConsenso_marketing(boolean consenso_marketing) {
        this.consenso_marketing = consenso_marketing;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}

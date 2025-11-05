package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "clienti")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int id_cliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "consenso_gdpr")
    private boolean consenso_gdpr;

    @Column(name = "consenso_marketing")
    private boolean consenso_marketing;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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
}
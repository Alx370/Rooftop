package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "proprietari")
public class Proprietario {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proprietario")
    private int id_proprietario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    // length=100 chosen to match DB schema (VARCHAR(100)) and keep consistent naming limits.

    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
    // Unique + not null to ensure one account per email.
    // length=150 chosen to allow long emails.

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "consenso_gdpr", nullable = false)
    private boolean consenso_gdpr;

    @Column(name = "consenso_marketing", nullable = false)
    private boolean consenso_marketing;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false, length = 20)
    private Stato stato;
    // Store user state as string ("ACTIVE", etc.)
    // EnumType.STRING ensures DB and Java stay aligned even if enum order changes.


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

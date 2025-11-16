package Immobiliaris.Progetto_Rooftop.Model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "proprietari")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proprietario")
    private int id_proprietario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Column(name = "email", unique = true, length = 150, nullable = false)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "consenso_gdpr", nullable = false)
    private boolean consenso_gdpr;

    @Column(name = "consenso_marketing", nullable = false)
    private boolean consenso_marketing;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false, length = 20)
    private RuoloProprietario ruolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false, length = 20)
    private Stato stato;

    @Column(name = "immagine_profilo")
    private String immagineProfilo;

    @OneToMany(mappedBy = "proprietario")
    private List<Immobile> immobili;

    public Proprietario() {
        // valori di default coerenti con DB
        this.ruolo = RuoloProprietario.PROPRIETARIO;
        this.stato = Stato.ATTIVO;
    }

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

    public Boolean isConsenso_gdpr() {
        return consenso_gdpr;
    }

    public void setConsenso_gdpr(boolean consenso_gdpr) {
        this.consenso_gdpr = consenso_gdpr;
    }

    public Boolean isConsenso_marketing() {
        return consenso_marketing;
    }

    public void setConsenso_marketing(boolean consenso_marketing) {
        this.consenso_marketing = consenso_marketing;
    }

    public RuoloProprietario getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloProprietario ruolo) {
        this.ruolo = ruolo;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public List<Immobile> getImmobili() {
        return immobili;
    }

    public void setImmobili(List<Immobile> immobili) {
        this.immobili = immobili;
    }
}

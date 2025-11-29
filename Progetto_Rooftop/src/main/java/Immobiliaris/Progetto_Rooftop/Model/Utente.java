package Immobiliaris.Progetto_Rooftop.Model;

import java.util.List;

import Immobiliaris.Progetto_Rooftop.Enum.Ruolo;
import Immobiliaris.Progetto_Rooftop.Enum.Stato;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "utenti")
public class Utente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private int id_utente;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    // length=100 chosen to match DB schema (VARCHAR(100)) and keep consistent naming limits.


    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
    // Unique + not null to ensure one account per email.
    // length=150 chosen to allow long emails (industry standard is usually 150–254 max).

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false, length = 20)
    private Ruolo ruolo;
    // Store user role as string ("AMMINISTRATORE", etc.)
    // EnumType.STRING ensures DB and Java stay aligned even if enum order changes.

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false, length = 20)
    private Stato stato;

    // Relazione OneToMany con Immobile (solo per proprietari)
    @OneToMany(mappedBy = "proprietario")
    private List<Immobile> immobili;

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public List<Immobile> getImmobili() {
        return immobili;
    }

    public void setImmobili(List<Immobile> immobili) {
        this.immobili = immobili;
    }
}

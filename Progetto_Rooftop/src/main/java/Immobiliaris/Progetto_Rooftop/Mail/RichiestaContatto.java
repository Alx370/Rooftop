package Immobiliaris.Progetto_Rooftop.Mail;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * CONTACT REQUEST ENTITY
 * Represents a contact request/FAQ saved in the database.
 * 
 * Fields:
 * - idRichiesta: auto-generated ID
 * - nome, cognome, email, telefono: customer data
 * - messaggio: the question/request
 * - dataRichiesta: when it was submitted
 * - stato: NUOVA, LETTA, COMPLETATA
 * - salvataComeFaq: whether the agent saved it as an FAQ
 */
@Entity
@Table(name = "richieste_contatto")
@Data
public class RichiestaContatto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRichiesta;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    private String telefono;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String messaggio;

    private LocalDateTime dataRichiesta = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatoRichiesta stato = StatoRichiesta.NUOVA;

    private Boolean salvataComeFaq = false;

    // Possible states of the request
    public enum StatoRichiesta {
        NUOVA,       // Just arrived
        IN_LAVORAZIONE,       // Agent has seen it
        COMPLETATA   // Agent has responded
    }
}

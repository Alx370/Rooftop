package Immobiliaris.Progetto_Rooftop.Mail;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * NEWSLETTER ENTITY
 * Represents a newsletter subscriber saved in the database.
 * 
 * Fields:
 * - idNewsletter: auto-generated ID
 * - email: subscriber's email (unique)
 * - dataIscrizione: subscription timestamp
 * - attivo: whether still subscribed (true/false)
 */
@Entity
@Table(name = "newsletter")
@Data
public class Newsletter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNewsletter;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDateTime dataIscrizione = LocalDateTime.now();

    private Boolean attivo = true;
}

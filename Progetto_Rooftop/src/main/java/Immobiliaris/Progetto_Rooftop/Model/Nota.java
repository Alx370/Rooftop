package Immobiliaris.Progetto_Rooftop.Model;

import java.time.LocalDateTime;

import Immobiliaris.Progetto_Rooftop.Enum.TipoNota;
import Immobiliaris.Progetto_Rooftop.Enum.VisibilitaNota;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entità JPA che mappa la tabella `note`.
 * Rappresenta una nota interna con agente autore, tipo, contenuto,
 * visibilità e timestamp di creazione.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;

    /** Immobile collegato (opzionale: la nota può non riferirsi ad un immobile). */
    @Column(name = "id_immobile")
    private Integer idImmobile;

    /** Agente autore della nota. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agente", nullable = false)
    private Utente agente;

    /** Tipo della nota (default: INTERNO). */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNota tipo = TipoNota.INTERNO;

    /** Contenuto testuale della nota. */
    @Column(name = "contenuto", nullable = false, columnDefinition = "TEXT")
    private String contenuto;

    /** Visibilità della nota (TEAM o PRIVATA, default: TEAM). */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "visibilita", nullable = false)
    private VisibilitaNota visibilita = VisibilitaNota.TEAM;

    /** Timestamp di creazione della nota (impostato in prePersist, non aggiornabile). */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (tipo == null) {
            tipo = TipoNota.INTERNO;
        }
        if (visibilita == null) {
            visibilita = VisibilitaNota.TEAM;
        }
    }
}
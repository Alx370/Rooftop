package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Integer id_nota;

    // Immobile collegato (opzionale: non sempre la nota è su un immobile specifico)
    @Column(name = "id_immobile")
    private Integer id_immobile;

    // Agente autore della nota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agente", nullable = false)
    private Utente agente;

    // Tipo nota (al momento solo "INTERNO")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNota tipodd = TipoNota.INTERNO;

    // Contenuto testuale della nota
    @Column(name = "contenuto", nullable = false, columnDefinition = "TEXT")
    private String contenuto;

    // Visibilità della nota (TEAM o PRIVATA)
    @Enumerated(EnumType.STRING)
    @Column(name = "visibilita")
    private VisibilitaNota visibilita = VisibilitaNota.TEAM;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
        if (tipo == null) {
            tipo = TipoNota.INTERNO;
        }
        if (visibilita == null) {
            visibilita = VisibilitaNota.TEAM;
        }
    }
}
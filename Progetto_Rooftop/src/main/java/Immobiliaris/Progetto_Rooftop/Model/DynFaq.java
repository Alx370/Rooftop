package Immobiliaris.Progetto_Rooftop.Model;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faq")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynFaq {

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
}

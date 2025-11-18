package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ValutazioneService {

    public BigDecimal valutaImmobile(Immobile immobile, CaratteristicheImmobile caratteristiche) {

        // ================================
        // 1️⃣ Valori di base con default = 1
        // ================================
        BigDecimal mq = defaultBigDecimal(immobile.getMetri_quadri());
        Integer locali = defaultInt(immobile.getLocali());
        Integer bagni = defaultInt(immobile.getBagni());

        // ================================
        // 2️⃣ Valutazione base €/mq
        // ================================
        BigDecimal valoreBaseMq = BigDecimal.valueOf(1500); // valore base arbitrario per test
        BigDecimal valore = mq.multiply(valoreBaseMq);

        // ================================
        // 3️⃣ Bonus per locali e bagni
        // ================================
        valore = valore.add(BigDecimal.valueOf(locali * 3000L)); // ogni locale aggiunge €3000
        valore = valore.add(BigDecimal.valueOf(bagni * 5000L));  // ogni bagno aggiunge €5000

        // ================================
        // 4️⃣ Bonus caratteristiche (molto semplice per test)
        // ================================
        valore = valore.add(addIfTrue(caratteristiche.getAscensore(), 5000));
        valore = valore.add(addIfTrue(caratteristiche.getParcheggio(), 4000));
        valore = valore.add(addIfTrue(caratteristiche.getGarage(), 8000));
        valore = valore.add(addIfTrue(caratteristiche.getBalcone(), 3000));
        valore = valore.add(addIfTrue(caratteristiche.getTerrazzo(), 7000));
        valore = valore.add(addIfTrue(caratteristiche.getGiardino(), 10000));
        valore = valore.add(addIfTrue(caratteristiche.getCantina(), 2000));
        valore = valore.add(addIfTrue(caratteristiche.getArredato(), 1500));
        valore = valore.add(addIfTrue(caratteristiche.getAria_condizionata(), 2500));
        valore = valore.add(addIfTrue(caratteristiche.getAllarme(), 2000));

        // ================================
        // 5️⃣ Aggiunta metratura balcone / terrazzo / giardino (bonus €100/mq)
        // ================================
        valore = valore.add(mqOrZero(caratteristiche.getBalcone_mq()).multiply(BigDecimal.valueOf(100)));
        valore = valore.add(mqOrZero(caratteristiche.getTerrazzo_mq()).multiply(BigDecimal.valueOf(150)));
        valore = valore.add(mqOrZero(caratteristiche.getGiardino_mq()).multiply(BigDecimal.valueOf(80)));

        // ================================
        // 6️⃣ Ritorna con due decimali
        // ================================
        return valore.setScale(2, RoundingMode.HALF_UP);
    }

    // ================================
    // 🔧 METODI DI SUPPORTO
    // ================================

    private Integer defaultInt(Integer value) {
        return (value == null) ? 1 : value;
    }

    private BigDecimal defaultBigDecimal(BigDecimal value) {
        return (value == null) ? BigDecimal.ONE : value;
    }

    private BigDecimal addIfTrue(Boolean flag, int amount) {
        return (flag != null && flag) ? BigDecimal.valueOf(amount) : BigDecimal.ZERO;
    }

    private BigDecimal mqOrZero(BigDecimal value) {
        return (value == null) ? BigDecimal.ZERO : value;
    }
}

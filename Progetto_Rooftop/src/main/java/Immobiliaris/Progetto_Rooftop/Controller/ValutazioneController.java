package Immobiliaris.Progetto_Rooftop.Controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Services.ValutazioneService;

@RestController
@RequestMapping("/api/valutazione")
public class ValutazioneController {

    private final ValutazioneService valutazioneService;

    public ValutazioneController(ValutazioneService valutazioneService) {
        this.valutazioneService = valutazioneService;
    }

    @PostMapping
    public ResponseEntity<BigDecimal> valutaImmobile(@RequestBody Map<String, Object> body) {

        Map<String, Object> immobileMap = (Map<String, Object>) body.get("immobile");
        Map<String, Object> caratteristicheMap = (Map<String, Object>) body.get("caratteristiche");

        Immobile immobile = new Immobile();
        CaratteristicheImmobile caratteristiche = new CaratteristicheImmobile();

        // ------------------------------
        // IMPOSTAZIONE DATI IMMOBILE
        // ------------------------------
        if (immobileMap != null) {
            immobile.setMetri_quadri(convertBigDecimal(immobileMap.get("metri_quadri")));
            immobile.setLocali(convertInteger(immobileMap.get("locali")));
            immobile.setBagni(convertInteger(immobileMap.get("bagni")));
        }

        // ------------------------------
        // IMPOSTAZIONE CARATTERISTICHE
        // ------------------------------
        if (caratteristicheMap != null) {
            caratteristiche.setImmobile(immobile);
            caratteristiche.setAscensore(convertBoolean(caratteristicheMap.get("ascensore")));
            caratteristiche.setParcheggio(convertBoolean(caratteristicheMap.get("parcheggio")));
            caratteristiche.setGarage(convertBoolean(caratteristicheMap.get("garage")));
            caratteristiche.setBalcone(convertBoolean(caratteristicheMap.get("balcone")));
            caratteristiche.setBalcone_mq(convertBigDecimal(caratteristicheMap.get("balcone_mq")));
            caratteristiche.setTerrazzo(convertBoolean(caratteristicheMap.get("terrazzo")));
            caratteristiche.setTerrazzo_mq(convertBigDecimal(caratteristicheMap.get("terrazzo_mq")));
            caratteristiche.setGiardino(convertBoolean(caratteristicheMap.get("giardino")));
            caratteristiche.setGiardino_mq(convertBigDecimal(caratteristicheMap.get("giardino_mq")));
            caratteristiche.setCantina(convertBoolean(caratteristicheMap.get("cantina")));
            caratteristiche.setArredato(convertBoolean(caratteristicheMap.get("arredato")));
            caratteristiche.setAria_condizionata(convertBoolean(caratteristicheMap.get("aria_condizionata")));
            caratteristiche.setAllarme(convertBoolean(caratteristicheMap.get("allarme")));
        }

        // CHIA MI QUELLO CHE TI INTERESSA DAVVERO
        BigDecimal valutazione = valutazioneService.valutaImmobile(immobile, caratteristiche);

        return ResponseEntity.ok(valutazione);
    }

    // ------------------------------
    //   HELPER PER I VALORI NULL
    // ------------------------------
    private BigDecimal convertBigDecimal(Object v) {
        if (v == null) return BigDecimal.ZERO;  // oppure 1 se preferisci
        return new BigDecimal(v.toString());
    }

    private Integer convertInteger(Object v) {
        if (v == null) return 1; // come richiesto
        return Integer.parseInt(v.toString());
    }

    private Boolean convertBoolean(Object v) {
        if (v == null) return false; // o true se preferisci
        return Boolean.parseBoolean(v.toString());
    }
}

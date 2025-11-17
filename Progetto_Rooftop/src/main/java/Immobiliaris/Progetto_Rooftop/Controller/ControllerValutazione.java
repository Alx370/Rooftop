package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;

@RestController
@RequestMapping("/api/valutazione")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControllerValutazione {

    @Autowired
    private ServiceImmobile serviceImmobile;

    // Endpoint per ottenere le tipologie di immobili disponibili
    @GetMapping("/tipologie")
    public ResponseEntity<List<String>> getTipologie() {
        List<String> tipologie = List.of(
            "APPARTAMENTO",
            "VILLA",
            "CASA_INDIPENDENTE",
            "ATTICO",
            "LOFT",
            "MANSARDA",
            "RUSTICO",
            "CASALE"
        );
        return ResponseEntity.ok(tipologie);
    }

    // Endpoint per ottenere gli stati disponibili
    @GetMapping("/stati")
    public ResponseEntity<List<String>> getStati() {
        List<String> stati = List.of(
            "OTTIMO",
            "BUONO",
            "DA_RISTRUTTURARE",
            "NUOVO"
        );
        return ResponseEntity.ok(stati);
    }

    // Endpoint per ottenere gli stati annuncio disponibili
    @GetMapping("/stati-annuncio")
    public ResponseEntity<List<String>> getStatiAnnuncio() {
        List<String> statiAnnuncio = List.of(
            "VALUTAZIONE",
            "PUBBLICATO",
            "VENDUTO",
            "RITIRATO",
            "SOSPESO"
        );
        return ResponseEntity.ok(statiAnnuncio);
    }

    // Endpoint per calcolare una stima del prezzo
    @PostMapping("/stima-prezzo")
    public ResponseEntity<Map<String, Object>> stimaPrezzo(@RequestBody Map<String, Object> data) {
        try {
            Map<String, Object> response = new HashMap<>();
            
            double basePrice = 1000; // €/m²
            String stato = (String) data.get("stato_immobile");
            String tipologia = (String) data.get("tipologia");
            double metriQuadri = ((Number) data.get("metri_quadri")).doubleValue();

            // Aggiusta il prezzo in base allo stato
            Map<String, Double> stateMultipliers = Map.of(
                "NUOVO", 1.3,
                "OTTIMO", 1.2,
                "BUONO", 1.0,
                "DA_RISTRUTTURARE", 0.6
            );
            basePrice *= stateMultipliers.getOrDefault(stato, 1.0);

            // Aggiusta il prezzo in base alla tipologia
            Map<String, Double> typeMultipliers = Map.of(
                "VILLA", 1.4,
                "ATTICO", 1.3,
                "APPARTAMENTO", 1.0,
                "CASA_INDIPENDENTE", 1.2,
                "LOFT", 1.1,
                "MANSARDA", 0.8,
                "RUSTICO", 0.5,
                "CASALE", 0.7
            );
            basePrice *= typeMultipliers.getOrDefault(tipologia, 1.0);

            // Calcola il prezzo totale
            long estimatedPrice = Math.round(basePrice * metriQuadri);

            response.put("estimatedPrice", estimatedPrice);
            response.put("pricePerSquareMeter", Math.round(basePrice));
            response.put("formatted", String.format("€ %,d", estimatedPrice));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Errore nel calcolo della stima");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // Endpoint per ottenere gli immobili in valutazione
    @GetMapping("/in-valutazione")
    public ResponseEntity<List<Immobile>> getPropertiesInValuation() {
        try {
            List<Immobile> immobili = serviceImmobile.getAll();
            // Filtra solo gli immobili in stato VALUTAZIONE
            List<Immobile> inValutation = immobili.stream()
                .filter(i -> "VALUTAZIONE".equals(i.getStato_annuncio().name()))
                .toList();
            
            return ResponseEntity.ok(inValutation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint per ottenere gli immobili per tipologia
    @GetMapping("/per-tipologia/{tipologia}")
    public ResponseEntity<List<Immobile>> getPropertiesByType(@PathVariable String tipologia) {
        try {
            List<Immobile> immobili = serviceImmobile.getAll();
            List<Immobile> filtered = immobili.stream()
                .filter(i -> i.getTipologia().name().equals(tipologia))
                .toList();
            
            return ResponseEntity.ok(filtered);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint per ottenere statistiche sulle valutazioni
    @GetMapping("/statistiche")
    public ResponseEntity<Map<String, Object>> getValuationStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            List<Immobile> immobili = serviceImmobile.getAll();

            // Conta immobili per stato
            long inValutation = immobili.stream()
                .filter(i -> "VALUTAZIONE".equals(i.getStato_annuncio().name()))
                .count();
            
            long pubblicati = immobili.stream()
                .filter(i -> "PUBBLICATO".equals(i.getStato_annuncio().name()))
                .count();

            // Prezzo medio
            double averagePrice = immobili.stream()
                .mapToDouble(i -> i.getPrezzo_richiesto() != null ? i.getPrezzo_richiesto().doubleValue() : 0)
                .average()
                .orElse(0);

            stats.put("totalProperties", immobili.size());
            stats.put("inValuation", inValutation);
            stats.put("published", pubblicati);
            stats.put("averagePrice", Math.round(averagePrice));

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint di test
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Connessione al backend Valutazione stabilita con successo");
        
        return ResponseEntity.ok(response);
    }
}

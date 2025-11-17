package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControllerHome {

    // Endpoint per recuperare le statistiche della piattaforma
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("immobili_totali", 150);
        stats.put("valutazioni_completate", 320);
        stats.put("clienti_soddisfatti", 285);
        stats.put("agenti_esperti", 12);
        
        return ResponseEntity.ok(stats);
    }

    // Endpoint per recuperare i servizi offerti
    @GetMapping("/services")
    public ResponseEntity<List<Map<String, Object>>> getServices() {
        List<Map<String, Object>> services = List.of(
            Map.of(
                "id", 1,
                "titolo", "Per venderlo",
                "descrizione", "Ti guidiamo nella vendita con metodo, trasparenza e attenzione al valore.",
                "icona", "finestra.png",
                "link", "/valutazione"
            ),
            Map.of(
                "id", 2,
                "titolo", "Per affittarlo",
                "descrizione", "Gestiamo tutto noi: inquilini, contratti e sicurezza del tuo immobile.",
                "icona", "letto.png",
                "link", "/valutazione"
            )
        );
        
        return ResponseEntity.ok(services);
    }

    // Endpoint per recuperare i vantaggi competitivi (USP - Unique Selling Points)
    @GetMapping("/usp")
    public ResponseEntity<List<Map<String, Object>>> getUSP() {
        List<Map<String, Object>> usp = List.of(
            Map.of(
                "id", 1,
                "titolo", "Valutazione in 72 ore",
                "sottotitolo", "Rapida, accurata, umana.",
                "descrizione", "Ti offriamo una valutazione entro 72 ore, verificata dai nostri esperti e basata su dati reali.",
                "icona", "edificio.png"
            ),
            Map.of(
                "id", 2,
                "titolo", "Metodo user-centred",
                "sottotitolo", "Sarai al centro di ogni scelta.",
                "descrizione", "Verrai affiancato con competenza per valorizzare al meglio il tuo immobile.",
                "icona", "chiavi.png"
            ),
            Map.of(
                "id", 3,
                "titolo", "Vendita in esclusiva",
                "sottotitolo", "Un impegno reciproco.",
                "descrizione", "Ci dedichiamo al tuo immobile con strategie mirate e trasparenza, per vendere al giusto valore.",
                "icona", "exclusiva.png"
            )
        );
        
        return ResponseEntity.ok(usp);
    }

    // Endpoint per iscrivere un utente alla newsletter
    @PostMapping("/newsletter/subscribe")
    public ResponseEntity<Map<String, Object>> subscribeNewsletter(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            
            if (email == null || email.isBlank()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Email non valida");
                return ResponseEntity.badRequest().body(error);
            }

            // TODO: Salvare l'email nel database se necessario
            // Per ora ritorniamo solo un messaggio di successo
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Grazie per la sottoscrizione!");
            response.put("email", email);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Errore nella sottoscrizione");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Endpoint per recuperare le proprietà in primo piano
    @GetMapping("/featured-properties")
    public ResponseEntity<Map<String, Object>> getFeaturedProperties() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Utilizza GET /api/immobili per le proprietà disponibili");
        response.put("endpoint", "/api/immobili");
        
        return ResponseEntity.ok(response);
    }

    // Endpoint di test
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Connessione al backend Home stabilita con successo");
        
        return ResponseEntity.ok(response);
    }
}

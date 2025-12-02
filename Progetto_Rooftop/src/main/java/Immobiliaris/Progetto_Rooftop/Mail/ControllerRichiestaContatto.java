package Immobiliaris.Progetto_Rooftop.Mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;

import java.util.List;
import java.util.Map;

/**
 * CONTACT REQUEST CONTROLLER
 * Exposes REST APIs for contact requests / FAQs.
 * 
 * ENDPOINTS:
 * - POST /api/contatto           → New request (from customer)
 * - GET /api/contatto            → List all requests (for agent)
 * - PUT /api/contatto/{id}/letta → Mark as read
 * - PUT /api/contatto/{id}/faq   → Save as FAQ
 * - DELETE /api/contatto/{id}    → Delete request
 */
@RestController
@RequestMapping("/api/contatto")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControllerRichiestaContatto {

    private final ServiceRichiestaContatto serviceRichiestaContatto;

    /**
     * NEW CONTACT REQUEST
     * 
     * Method: POST
     * URL: /api/contatto
     * Body JSON: { "name": "Mario", "surname": "Rossi", "email": "...", "telephone": "...", "message": "..." }
     */
    @PostMapping
    public ResponseEntity<?> creaRichiesta(@RequestBody Map<String, String> body) {
        try {
            RichiestaContatto richiesta = serviceRichiestaContatto.creaRichiesta(
                body.get("nome"),
                body.get("cognome"),
                body.get("email"),
                body.get("telefono"),
                body.get("messaggio")
            );
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Richiesta inviata! Ti contatteremo presto.",
                "data", richiesta
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    /**
     * LIST ALL REQUESTS (for agent)
     * 
     * Method: GET
     * URL: /api/contatto
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<List<RichiestaContatto>> getTutteLeRichieste() {
        return ResponseEntity.ok(serviceRichiestaContatto.getTutteLeRichieste());
    }

    /**
     * LIST REQUESTS BY STATUS
     * 
     * Method: GET
     * URL: /api/contatto/stato/NUOVA  (or LETTA, COMPLETATA)
     */
    @GetMapping("/stato/{stato}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<List<RichiestaContatto>> getRichiestePerStato(
            @PathVariable RichiestaContatto.StatoRichiesta stato) {
        return ResponseEntity.ok(serviceRichiestaContatto.getRichiestePerStato(stato));
    }

    /**
     * MARK AS READ
     * 
     * Method: PUT
     * URL: /api/contatto/{id}/in-lavorazione
     */
    @PutMapping("/{id}/in-lavorazione")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<?> marcaComeInLavorazione(@PathVariable Integer id) {
        try {
            RichiestaContatto updated = serviceRichiestaContatto.marcaComeInLavorazione(id);
            return ResponseEntity.ok(Map.of("success", true, "data", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * SAVE AS FAQ
     * 
     * Method: PUT
     * URL: /api/contatto/{id}/faq
     * Body JSON: { "risposta": "La risposta...", "categoria": "GENERALE" }
     */
    @PutMapping("/{id}/faq")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<?> salvaComeFaq(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String risposta = body.get("risposta");
            String categoriaStr = body.get("categoria");
            CategoriaFaq categoria = categoriaStr != null ? CategoriaFaq.valueOf(categoriaStr) : CategoriaFaq.GENERALE;
            
            RichiestaContatto updated = serviceRichiestaContatto.salvaComeFaq(id, risposta, categoria);
            return ResponseEntity.ok(Map.of("success", true, "message", "Salvato come FAQ e notificato l'utente!", "data", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * DELETE REQUEST
     * 
     * Method: DELETE
     * URL: /api/contatto/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<?> eliminaRichiesta(@PathVariable Integer id) {
        try {
            serviceRichiestaContatto.eliminaRichiesta(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Richiesta eliminata."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

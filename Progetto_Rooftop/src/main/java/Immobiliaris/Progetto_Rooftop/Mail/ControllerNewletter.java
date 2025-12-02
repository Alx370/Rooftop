package Immobiliaris.Progetto_Rooftop.Mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * NEWSLETTER CONTROLLER
 * Exposes REST APIs for the newsletter.
 * 
 * ENDPOINTS:
 * - POST /api/newsletter/iscriviti   → Subscribe a new email
 * - DELETE /api/newsletter/disiscrivi/{email} → Unsubscribe an email
 */
@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControllerNewletter {

    private final ServiceNewsletter serviceNewsletter;

    /**
     * NEWSLETTER SUBSCRIPTION
     * 
     * Method: POST
     * URL: /api/newsletter/iscriviti
     * Body JSON: { "email": "user@example.com" }
     * 
     * Success Response: { "success": true, "message": "...", "data": {...} }
     * Error Response: { "success": false, "message": "..." }
     */
    @PostMapping("/iscriviti")
    public ResponseEntity<?> iscriviti(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            Newsletter newsletter = serviceNewsletter.iscriviNewsletter(email);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Iscrizione completata! Ti abbiamo inviato una email di conferma.",
                "data", newsletter
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    /**
     * NEWSLETTER UNSUBSCRIPTION
     * 
     * Method: DELETE
     * URL: /api/newsletter/disiscrivi/{email}
     * 
     * Example: DELETE /api/newsletter/disiscrivi/user@example.com
     */
    @DeleteMapping("/disiscrivi/{email}")
    public ResponseEntity<?> disiscrivi(@PathVariable String email) {
        try {
            serviceNewsletter.disiscriviNewsletter(email);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Disiscrizione completata."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

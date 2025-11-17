package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.HashMap;
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
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControllerAuthExtended {

    // Endpoint per verificare lo stato della sessione
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            response.put("authenticated", true);
            response.put("principal", auth.getPrincipal().toString());
            response.put("authorities", auth.getAuthorities());
        } else {
            response.put("authenticated", false);
            response.put("message", "Utente non autenticato");
        }
        
        return ResponseEntity.ok(response);
    }

    // Endpoint per validare il token
    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            
            if (token == null || token.isBlank()) {
                Map<String, Object> error = new HashMap<>();
                error.put("valid", false);
                error.put("message", "Token non fornito");
                return ResponseEntity.badRequest().body(error);
            }

            var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("principal", auth.getPrincipal().toString());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("valid", false);
                error.put("message", "Token non valido o scaduto");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("valid", false);
            error.put("message", "Errore nella validazione del token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Endpoint per verificare il tempo rimanente del token
    @GetMapping("/token-info")
    public ResponseEntity<Map<String, Object>> getTokenInfo() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            response.put("authenticated", true);
            response.put("principal", auth.getPrincipal().toString());
            response.put("message", "Token è valido");
            // Nota: il tempo di scadenza dipende dalla configurazione del JWT nel backend
        } else {
            response.put("authenticated", false);
            response.put("message", "Nessun token valido");
        }
        
        return ResponseEntity.ok(response);
    }

    // Endpoint di test
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Connessione al backend Auth stabilita con successo");
        
        return ResponseEntity.ok(response);
    }
}

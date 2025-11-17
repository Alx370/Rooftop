package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.HashMap;
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

import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;

@RestController
@RequestMapping("/api/registrazione")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControllerRegistrazione {

    @Autowired
    private ServiceUtente serviceUtente;

    // Endpoint per validare se un'email è disponibile
    @GetMapping("/email-disponibile/{email}")
    public ResponseEntity<Map<String, Object>> emailDisponibile(@PathVariable String email) {
        try {
            Utente utente = serviceUtente.getByEmail(email);
            
            Map<String, Object> response = new HashMap<>();
            if (utente == null) {
                response.put("disponibile", true);
                response.put("email", email);
                return ResponseEntity.ok(response);
            } else {
                response.put("disponibile", false);
                response.put("email", email);
                response.put("message", "Email già registrata");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("disponibile", false);
            error.put("error", "Errore nella verifica email");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Endpoint per ottenere i requisiti della password
    @GetMapping("/password-requirements")
    public ResponseEntity<Map<String, Object>> passwordRequirements() {
        Map<String, Object> response = new HashMap<>();
        response.put("minLength", 8);
        response.put("requireUppercase", true);
        response.put("requireLowercase", true);
        response.put("requireNumbers", true);
        response.put("requireSpecialChars", true);
        response.put("specialCharsAllowed", "!@#$%^&*");
        response.put("description", "La password deve contenere almeno 8 caratteri, una lettera maiuscola, una minuscola, un numero e un carattere speciale");
        
        return ResponseEntity.ok(response);
    }

    // Endpoint per validare dati registrazione
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateRegistration(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();
        boolean valid = true;
        Map<String, String> errors = new HashMap<>();

        try {
            String email = data.get("email");
            String password = data.get("password");
            String nome = data.get("nome");
            String cognome = data.get("cognome");

            // Validazione email
            if (email == null || email.isBlank()) {
                valid = false;
                errors.put("email", "Email obbligatoria");
            } else if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                valid = false;
                errors.put("email", "Formato email non valido");
            } else {
                try {
                    Utente existingUser = serviceUtente.getByEmail(email);
                    if (existingUser != null) {
                        valid = false;
                        errors.put("email", "Email già registrata");
                    }
                } catch (Exception e) {
                    // Email non trovata, è disponibile
                }
            }

            // Validazione password
            if (password == null || password.isBlank()) {
                valid = false;
                errors.put("password", "Password obbligatoria");
            } else {
                if (password.length() < 8) {
                    valid = false;
                    errors.put("password", "Password deve contenere almeno 8 caratteri");
                }
                if (!password.matches(".*[A-Z].*")) {
                    valid = false;
                    errors.put("password", "Password deve contenere una lettera maiuscola");
                }
                if (!password.matches(".*[a-z].*")) {
                    valid = false;
                    errors.put("password", "Password deve contenere una lettera minuscola");
                }
                if (!password.matches(".*[0-9].*")) {
                    valid = false;
                    errors.put("password", "Password deve contenere un numero");
                }
                if (!password.matches(".*[!@#$%^&*].*")) {
                    valid = false;
                    errors.put("password", "Password deve contenere un carattere speciale");
                }
            }

            // Validazione nome
            if (nome == null || nome.isBlank()) {
                valid = false;
                errors.put("nome", "Nome obbligatorio");
            }

            // Validazione cognome
            if (cognome == null || cognome.isBlank()) {
                valid = false;
                errors.put("cognome", "Cognome obbligatorio");
            }

            response.put("valid", valid);
            response.put("errors", errors);

        } catch (Exception e) {
            response.put("valid", false);
            response.put("error", "Errore nella validazione");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    // Endpoint di test
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Connessione al backend Registrazione stabilita con successo");
        
        return ResponseEntity.ok(response);
    }
}

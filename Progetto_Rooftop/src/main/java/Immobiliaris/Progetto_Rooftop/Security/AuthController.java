package Immobiliaris.Progetto_Rooftop.Security;

import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import Immobiliaris.Progetto_Rooftop.Enum.Ruolo;
import Immobiliaris.Progetto_Rooftop.Enum.Stato;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final ServiceUtente serviceUtente;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    @Value("${app.google.client-id}")
    private String googleClientId;

    public AuthController(ServiceUtente serviceUtente, PasswordEncoder encoder, JwtService jwt) {
        this.serviceUtente = serviceUtente;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public static record LoginReq(String email, String password) {}
    public static record LoginRes(String token) {}
    public static record GoogleLoginReq(String idToken) {}

    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq req) {
        Utente u = serviceUtente.getByEmail(req.email()); // implementa nel tuo service/repo
        if (u == null || !encoder.matches(req.password(), u.getPassword())) {
            throw new RuntimeException("Credenziali non valide");
        }
        String token = jwt.generateToken(
                String.valueOf(u.getId_utente()),
                Map.of("ruolo", u.getRuolo().name(), "email", u.getEmail())
        );
        return new LoginRes(token);
    }

    @PostMapping("/google")
    public LoginRes google(@RequestBody GoogleLoginReq req) {
        try {
            var verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();
            GoogleIdToken idToken = verifier.verify(req.idToken());
            if (idToken == null) {
                throw new RuntimeException("Token Google non valido");
            }
            var payload = idToken.getPayload();
            String email = payload.getEmail();
            String givenName = (String) payload.get("given_name");
            String familyName = (String) payload.get("family_name");
            if (givenName == null || givenName.isBlank()) {
                String localPart = email != null ? email.split("@")[0] : "Utente";
                givenName = localPart;
            }
            if (familyName == null || familyName.isBlank()) {
                familyName = "Google";
            }

            Utente u;
            try {
                u = serviceUtente.getByEmail(email);
            } catch (Exception e) {
                u = new Utente();
                u.setNome(givenName);
                u.setCognome(familyName);
                u.setEmail(email);
                u.setPassword(UUID.randomUUID().toString());
                u.setRuolo(Ruolo.PROPRIETARIO);
                u.setStato(Stato.ATTIVO);
                u = serviceUtente.create(u);
            }

            String token = jwt.generateToken(
                    String.valueOf(u.getId_utente()),
                    Map.of("ruolo", u.getRuolo().name(), "email", u.getEmail())
            );
            return new LoginRes(token);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Errore login Google");
        }
    }

    /**
     * Logout (stateless with JWT).
     * The client must delete the token on the frontend.
     * The token remains technically valid until its natural expiration.
     */
    @PostMapping("/logout")
    public Map<String, String> logout() {
        return Map.of(
            "message", "Logout effettuato con successo"
        );
    }

    // debug endpoint to verify authentication and get user details
    @GetMapping("/me")
    public Map<String, Object> me() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || "anonymousUser".equals(auth.getPrincipal())) {
            return Map.of(
                "authenticated", false,
                "message", "No authentication found - user is anonymous"
            );
        }
        
        // Il principal contiene l'ID utente (string)
        String userId = auth.getPrincipal().toString();
        Utente utente = serviceUtente.getById(Integer.parseInt(userId));
        
        return Map.of(
            "authenticated", true,
            "nome", utente.getNome(),
            "cognome", utente.getCognome(),
            "email", utente.getEmail(),
            "telefono", utente.getTelefono() != null ? utente.getTelefono() : "",
            "stato", utente.getStato().name(),
            "authorities", auth.getAuthorities()
        );
    }
}

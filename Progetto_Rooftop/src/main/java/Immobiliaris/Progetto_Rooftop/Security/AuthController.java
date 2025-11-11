package Immobiliaris.Progetto_Rooftop.Security;

import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final ServiceUtente serviceUtente;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(ServiceUtente serviceUtente, PasswordEncoder encoder, JwtService jwt) {
        this.serviceUtente = serviceUtente;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public static record LoginReq(String email, String password) {}
    public static record LoginRes(String token) {}

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

    // comodo per debug rapido del filtro JWT
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

package Immobiliaris.Progetto_Rooftop.Mail;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/valutazioni")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControllerValutazioneManuale {

    private final EmailService emailService;
    private final Immobiliaris.Progetto_Rooftop.Services.ServiceUtente serviceUtente;

    @Value("${app.email.agente:admin@rooftop-immobiliare.it}")
    private String emailAgente;

    @PostMapping("/manuale")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> inviaValutazioneManuale(@RequestBody Map<String, Object> body) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth != null ? auth.getPrincipal().toString() : null;
        String tipo = String.valueOf(body.getOrDefault("tipo", "VALUTAZIONE"));
        String nome = String.valueOf(body.getOrDefault("nome", ""));
        String cognome = String.valueOf(body.getOrDefault("cognome", ""));
        String email = String.valueOf(body.getOrDefault("email", ""));
        String telefono = String.valueOf(body.getOrDefault("telefono", ""));
        String indirizzo = String.valueOf(body.getOrDefault("indirizzo", ""));
        String provincia = String.valueOf(body.getOrDefault("provincia", ""));
        String cap = String.valueOf(body.getOrDefault("cap", ""));
        String tipologia = String.valueOf(body.getOrDefault("tipologia", ""));
        String serviceType = String.valueOf(body.getOrDefault("serviceType", ""));
        String sellingType = String.valueOf(body.getOrDefault("sellingType", ""));
        String contractType = String.valueOf(body.getOrDefault("contractType", ""));

        String oggetto = "Nuova Valutazione Manuale - " + tipo;

        String html = "<html><body style=\"font-family: Arial; padding: 20px;\">"
                + "<h2 style=\"color:#ff7a00;\">Valutazione Manuale</h2>"
                + String.format("<p><strong>Nome:</strong> %s %s</p>", nome, cognome)
                + String.format("<p><strong>Email:</strong> %s</p>", email)
                + String.format("<p><strong>Telefono:</strong> %s</p>", telefono)
                + String.format("<p><strong>Indirizzo:</strong> %s</p>", indirizzo)
                + String.format("<p><strong>Provincia:</strong> %s</p>", provincia)
                + String.format("<p><strong>CAP:</strong> %s</p>", cap)
                + String.format("<p><strong>Tipologia:</strong> %s</p>", tipologia)
                + String.format("<p><strong>Servizio:</strong> %s</p>", serviceType)
                + String.format("<p><strong>Vendita:</strong> %s</p>", sellingType)
                + String.format("<p><strong>Affitto:</strong> %s</p>", contractType)
                + (userId != null ? String.format("<p><strong>Utente autenticato:</strong> %s %s (%s)</p>",
                    serviceUtente.getById(Integer.parseInt(userId)).getNome(),
                    serviceUtente.getById(Integer.parseInt(userId)).getCognome(),
                    serviceUtente.getById(Integer.parseInt(userId)).getEmail()) : "")
                + "<hr><p>Dettagli completi:</p><pre style=\"background:#f7f7f7; padding:12px;\">"
                + body.toString()
                + "</pre><p>Rispondere al cliente entro 3 giorni.</p></body></html>";

        String replyTo = userId != null ? serviceUtente.getById(Integer.parseInt(userId)).getEmail() : null;
        emailService.inviaEmailConReplyTo(emailAgente, oggetto, html, replyTo);
        return ResponseEntity.ok(Map.of("success", true, "message", "Valutazione inviata, risposta entro 3 giorni"));
    }
}

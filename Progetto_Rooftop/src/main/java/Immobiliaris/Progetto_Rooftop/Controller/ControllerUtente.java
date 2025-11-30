package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Enum.Ruolo;
import Immobiliaris.Progetto_Rooftop.Enum.Stato;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utenti")
public class ControllerUtente {

    @Autowired
    private ServiceUtente serviceUtente;

    /**
     * GET /api/utenti
     * Retrieves all users
     */
    @GetMapping
    @PreAuthorize("hasRole('AMMINISTRATORE')") // permit viewing only by AMMINISTRATORE
    public ResponseEntity<List<Utente>> getAllUtenti() {
        List<Utente> utenti = serviceUtente.getAll();
        return ResponseEntity.ok(utenti);
    }

    /**
     * GET /api/utenti/{id}
     * Retrieves a user by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')") // permit viewing only by AMMINISTRATORE
    public ResponseEntity<Utente> getUtenteById(@PathVariable Integer id) {
        Utente utente = serviceUtente.getById(id);
        return ResponseEntity.ok(utente);
    }

    /**
     * GET /api/utenti/email/{email}
     * Retrieves a user by email
     */
    @PreAuthorize("hasRole('AMMINISTRATORE')") // permit viewing only by AMMINISTRATORE
    @GetMapping("/email/{email}")
    public ResponseEntity<Utente> getUtenteByEmail(@PathVariable String email) {
        Utente utente = serviceUtente.getByEmail(email);
        return ResponseEntity.ok(utente);
    }

    /**
     * GET /api/utenti/ruolo/{ruolo}
     * Retrieves all users with a specific role
     */
    @GetMapping("/ruolo/{ruolo}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE')")
    public ResponseEntity<List<Utente>> getUtentiByRuolo(@PathVariable Ruolo ruolo) {
        List<Utente> utenti = serviceUtente.getByRuolo(ruolo);
        return ResponseEntity.ok(utenti);
    }

    /**
     * GET /api/utenti/stato/{stato}
     * Retrieves all users with a specific status
     */
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE', 'VALUTATORE')") // permit viewing by AMMINISTRATORE, AGENTE, VALUTATORE
    @GetMapping("/stato/{stato}")
    public ResponseEntity<List<Utente>> getUtentiByStato(@PathVariable Stato stato) {
        List<Utente> utenti = serviceUtente.getByStato(stato);
        return ResponseEntity.ok(utenti);
    }

    /**
     * GET /api/utenti/ruoli
     * Returns the available roles (enum names) so the frontend can populate selects
     */
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE', 'VALUTATORE')") // permit viewing by AMMINISTRATORE, AGENTE, VALUTATORE
    @GetMapping("/ruoli")
    public ResponseEntity<List<String>> getAvailableRuoli() {
        List<String> ruoli = Arrays.stream(Ruolo.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ruoli);
    }

    /**
     * GET /api/utenti/stati
     * Returns the available states (enum names) so the frontend can populate selects
     */
    @GetMapping("/stati")
    public ResponseEntity<List<String>> getAvailableStati() {
        List<String> stati = Arrays.stream(Stato.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stati);
    }

    /**
     * POST /api/utenti
     * Creates a new user
     */
    @PostMapping
    @PreAuthorize("hasRole('AMMINISTRATORE')") // create other user/admin only by AMMINISTRATORE
    public ResponseEntity<Utente> createUtente(@RequestBody Utente utente) {
        Utente nuovoUtente = serviceUtente.create(utente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
    }

    /**
     * POST /api/utenti/registrati
     * Public endpoint to register a new property owner
     * This endpoint is accessible without authentication and forces the role to PROPRIETARIO
     */
    @PostMapping("/registrati")
    public ResponseEntity<Utente> registraProprietario(@RequestBody Utente utente) {
        // Force the role to PROPRIETARIO regardless of what is sent in the request
        utente.setRuolo(Ruolo.PROPRIETARIO);
        // Force the status to ATTIVO
        utente.setStato(Stato.ATTIVO);
        
        Utente nuovoProprietario = serviceUtente.create(utente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoProprietario);
    }

    /**
     * PUT /api/utenti/{id}
     * Updates an existing user
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')") // update only by AMMINISTRATORE
    public ResponseEntity<Utente> updateUtente(
            @PathVariable Integer id,
            @RequestBody Utente utente) {
        Utente utenteAggiornato = serviceUtente.update(id, utente);
        return ResponseEntity.ok(utenteAggiornato);
    }

    /**
     * PATCH /api/utenti/{id}/password
     * Updates only the password of a user
     */
    @PatchMapping("/{id}/password")
    @PreAuthorize("hasRole('AMMINISTRATORE') or #id == authentication.principal") // permit AMMINISTRATORE or the user themselves to update password
    public ResponseEntity<Void> updatePassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> payload) {
        String newPassword = payload.get("newPassword");
        serviceUtente.updatePassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/utenti/{id}
     * Deletes a user
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')") // delete only by AMMINISTRATORE
    public ResponseEntity<Void> deleteUtente(@PathVariable Integer id) {
        serviceUtente.delete(id);
        return ResponseEntity.noContent().build();
    }
}

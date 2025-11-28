package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Model.Valutazione;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;

@RestController
@RequestMapping("/api/immobili")
public class ControllerImmobile {

    @Autowired
    private ServiceImmobile serviceImmobile;

    @Autowired
    private ServiceUtente serviceUtente;

    @GetMapping
    public ResponseEntity<List<Immobile>> getAllImmobili() {
        List<Immobile> immobili = serviceImmobile.getAll();
        if (immobili.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(immobili);
    }

    @PostMapping("/{id}/valutazione/automatica")
    public ResponseEntity<Valutazione> stimaAutomatica(@PathVariable Integer id, @RequestBody Map<String, BigDecimal> payload) {
        BigDecimal prezzoMqZona = payload.get("prezzoMqZona");
        if (prezzoMqZona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prezzoMqZona mancante");
        }
        Valutazione v = serviceImmobile.stimaAutomatica(id, prezzoMqZona);
        return ResponseEntity.ok(v);
    }

    @PostMapping("/{id}/valutazione/automatica/omi")
    public ResponseEntity<Valutazione> stimaAutomaticaDaOmi(@PathVariable Integer id) {
        Valutazione v = serviceImmobile.stimaAutomaticaDaOMI(id);
        return ResponseEntity.ok(v);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImmobileById(@PathVariable Integer id) {
        try {
            Immobile immobile = serviceImmobile.getById(id);
            return ResponseEntity.ok(immobile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createImmobile(@RequestBody Immobile immobile) {
        try {
            // Retrieve current authentication
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String userId = auth.getPrincipal().toString();
                Utente utente = serviceUtente.getById(Integer.parseInt(userId));
                
                // if the user is logged in as a PROPRIETARIO, find their proprietario profile
                if ("PROPRIETARIO".equals(utente.getRuolo().name())) {
                    immobile.setProprietario(utente);
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
                        "Solo i proprietari possono creare immobili");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
                    "Autenticazione richiesta per creare un immobile");
            }
            
            Immobile nuovoImmobile = serviceImmobile.create(immobile);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoImmobile); 
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImmobile(@PathVariable Integer id, @RequestBody Immobile immobile) {
        try {
            Immobile aggiornato = serviceImmobile.update(id, immobile);
            return ResponseEntity.ok(aggiornato); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImmobile(@PathVariable Integer id) {
        try {
            serviceImmobile.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;

/**
 * Controller per la gestione CRUD degli immobili.
 * Le funzionalità di valutazione automatica sono state spostate in ControllerValutazione.
 */

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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String userId = auth.getPrincipal().toString();
                Utente utente = serviceUtente.getById(Integer.parseInt(userId));
                
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
            return ResponseEntity.ok(aggiornato);
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

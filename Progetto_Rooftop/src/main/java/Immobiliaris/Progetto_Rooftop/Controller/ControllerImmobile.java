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
import Immobiliaris.Progetto_Rooftop.Model.Proprietario;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;
import Immobiliaris.Progetto_Rooftop.Services.ServicePropietario;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;

@RestController
@RequestMapping("/api/immobili")
public class ControllerImmobile {

    @Autowired
    private ServiceImmobile serviceImmobile;

    @Autowired
    private ServiceUtente serviceUtente;

    @Autowired
    private ServicePropietario servicePropietario;

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
            // Retrieve current authentication
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String userId = auth.getPrincipal().toString();
                Utente utente = serviceUtente.getById(Integer.parseInt(userId));
                
                // if the user is logged in as a PROPRIETARIO, find their proprietario profile
                if ("PROPRIETARIO".equals(utente.getRuolo().name())) {
                    Proprietario proprietario = servicePropietario.getByEmail(utente.getEmail());
                    
                    if (proprietario == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                            "Profilo proprietario non trovato per questo utente");
                    }
                    
                    // Automatically set the id_proprietario
                    immobile.setId_proprietario(proprietario.getId_proprietario());
                }
            }
            
            Immobile nuovoImmobile = serviceImmobile.create(immobile);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoImmobile); 
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

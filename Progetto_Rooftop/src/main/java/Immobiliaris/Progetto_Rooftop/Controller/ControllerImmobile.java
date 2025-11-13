package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;

@RestController
@RequestMapping("/api/immobili")
public class ControllerImmobile {

    @Autowired
    private ServiceImmobile serviceImmobile;

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

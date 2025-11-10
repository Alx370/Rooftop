package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Recensione;
import Immobiliaris.Progetto_Rooftop.Services.ServiceRecensione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller exposing CRUD endpoints for managing Recensioni (reviews).
 * Delegates business logic to ServiceRecensione.
 */
@RestController
@RequestMapping("/api/recensioni")
public class ControllerRecensione {

    @Autowired
    private ServiceRecensione serviceRecensione;

    /** GET /api/recensioni - returns all reviews ordered by creation timestamp desc. */
    @GetMapping
    public ResponseEntity<List<Recensione>> getAll() {
        return ResponseEntity.ok(serviceRecensione.getAllOrdered());
    }

    /** GET /api/recensioni/{id} - returns a review by id. */
    @GetMapping("/{id}")
    public ResponseEntity<Recensione> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceRecensione.getById(id));
    }

    /** GET /api/recensioni/agente/{idAgente} - returns reviews for a given agent. */
    @GetMapping("/agente/{idAgente}")
    public ResponseEntity<List<Recensione>> getByAgente(@PathVariable Integer idAgente) {
        return ResponseEntity.ok(serviceRecensione.getByAgente(idAgente));
    }

    /** GET /api/recensioni/immobile/{idImmobile} - returns reviews for a given immobile. */
    @GetMapping("/immobile/{idImmobile}")
    public ResponseEntity<List<Recensione>> getByImmobile(@PathVariable Integer idImmobile) {
        return ResponseEntity.ok(serviceRecensione.getByImmobile(idImmobile));
    }

    /** POST /api/recensioni - creates a new review. */
    @PostMapping
    public ResponseEntity<Recensione> create(@RequestBody Recensione recensione) {
        Recensione created = serviceRecensione.create(recensione);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/recensioni/{id} - updates an existing review. */
    @PutMapping("/{id}")
    public ResponseEntity<Recensione> update(@PathVariable Integer id, @RequestBody Recensione updated) {
        Recensione saved = serviceRecensione.update(id, updated);
        return ResponseEntity.ok(saved);
    }

    /** PATCH /api/recensioni/{id}/verifica - toggles verification status. */
    @PatchMapping("/{id}/verifica")
    public ResponseEntity<Recensione> setVerificata(
            @PathVariable Integer id,
            @RequestBody Map<String, Boolean> payload) {
        Boolean verificata = payload.get("verificata");
        if (verificata == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        Recensione saved = serviceRecensione.setVerificata(id, verificata);
        return ResponseEntity.ok(saved);
    }

    /** DELETE /api/recensioni/{id} - deletes a review by id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        serviceRecensione.delete(id);
        return ResponseEntity.noContent().build();
    }
}
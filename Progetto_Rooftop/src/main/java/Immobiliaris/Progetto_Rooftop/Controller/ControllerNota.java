package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Services.ServiceNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller per la gestione delle Note interne.
 * Espone endpoint CRUD e di filtro per agente, immobile e visibilità,
 * delegando la logica applicativa al {@link Immobiliaris.Progetto_Rooftop.Services.ServiceNota}.
 */
@RestController
@RequestMapping("/api/note")
public class ControllerNota {

    @Autowired
    private ServiceNota serviceNota;

    /** Restituisce tutte le note ordinate per data di creazione decrescente. */
    @GetMapping
    public ResponseEntity<List<Nota>> getAll() {
        return ResponseEntity.ok(serviceNota.getAllOrdered());
    }

    /** Restituisce una singola nota tramite id. */
    @GetMapping("/{id}")
    public ResponseEntity<Nota> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceNota.getById(id));
    }

    /**
     * Restituisce le note associate a un immobile. Se specificata, filtra per visibilità.
     */
    @GetMapping("/immobili/{idImmobile}")
    public ResponseEntity<List<Nota>> getByImmobile(
            @PathVariable Integer idImmobile,
            @RequestParam(name = "visibilita", required = false) VisibilitaNota visibilita
    ) {
        if (visibilita != null) {
            return ResponseEntity.ok(serviceNota.getByImmobileAndVisibilita(idImmobile, visibilita));
        }
        return ResponseEntity.ok(serviceNota.getByImmobile(idImmobile));
    }

    /** Restituisce le note dell'agente indicato. */
    @GetMapping("/agenti/{idAgente}")
    public ResponseEntity<List<Nota>> getByAgente(@PathVariable Integer idAgente) {
        return ResponseEntity.ok(serviceNota.getByAgente(idAgente));
    }

    /** Crea una nuova nota. */
    @PostMapping
    public ResponseEntity<Nota> create(@RequestBody Nota nota) {
        Nota saved = serviceNota.create(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Aggiorna una nota esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody Nota input) {
        Nota updated = serviceNota.update(id, input);
        return ResponseEntity.ok(updated);
    }

    /** Elimina la nota con l'id fornito. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        serviceNota.delete(id);
        return ResponseEntity.noContent().build();
    }
}
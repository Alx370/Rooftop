package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Services.ServiceNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class ControllerNota {

    @Autowired
    private ServiceNota serviceNota;

    @GetMapping
    public ResponseEntity<List<Nota>> getAll() {
        return ResponseEntity.ok(serviceNota.getAllOrdered());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceNota.getById(id));
    }

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

    @GetMapping("/agenti/{idAgente}")
    public ResponseEntity<List<Nota>> getByAgente(@PathVariable Integer idAgente) {
        return ResponseEntity.ok(serviceNota.getByAgente(idAgente));
    }

    @PostMapping
    public ResponseEntity<Nota> create(@RequestBody Nota nota) {
        Nota saved = serviceNota.create(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody Nota input) {
        Nota updated = serviceNota.update(id, input);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        serviceNota.delete(id);
        return ResponseEntity.noContent().build();
    }
}
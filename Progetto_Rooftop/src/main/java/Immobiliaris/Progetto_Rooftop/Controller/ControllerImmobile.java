package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/immobili")
public class ControllerImmobile {

    @Autowired
    private ServiceImmobile serviceImmobile;

    @GetMapping
    public ResponseEntity<List<Immobile>> getAllImmobili() {
        List<Immobile> immobile = serviceImmobile.getAll();
        return ResponseEntity.ok(immobile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Immobile> getImmobileById(@PathVariable Integer id) {
        Immobile immobili = serviceImmobile.getById();
        return ResponseEntity.ok(immobili);
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<Immobile> updateImmobile(@PathVariable Integer id, @RequestBody Immobile immobile) {
        Immobile aggiornato = serviceImmobile.update(id, immobile);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImmobile(@PathVariable Integer id) {
        serviceImmobile.delete(id);
        return ResponseEntity.noContent().build();

    }
}

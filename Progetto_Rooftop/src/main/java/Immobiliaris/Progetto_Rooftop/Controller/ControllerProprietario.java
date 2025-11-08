package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Proprietario;
import Immobiliaris.Progetto_Rooftop.Model.Stato;
import Immobiliaris.Progetto_Rooftop.Services.ServicePropietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proprietari")
public class ControllerProprietario {

    @Autowired
    private ServicePropietario servicePropietario;

    /**
     * GET /api/proprietari
     * Retrieves all owners
     */
    @GetMapping
    public ResponseEntity<List<Proprietario>> getAllProprietari() {
        List<Proprietario> proprietari = servicePropietario.getAll();
        return ResponseEntity.ok(proprietari);
    }

    /**
     * GET /api/proprietari/{id}
     * Retrieves an owner by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> getProprietarioById(@PathVariable Integer id) {
        Proprietario proprietario = servicePropietario.getById(id);
        return ResponseEntity.ok(proprietario);
    }

    /**
     * GET /api/proprietari/email/{email}
     * Retrieves an owner by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Proprietario> getProprietarioByEmail(@PathVariable String email) {
        Proprietario proprietario = servicePropietario.getByEmail(email);
        return ResponseEntity.ok(proprietario);
    }

    /**
     * GET /api/proprietari/stato/{stato}
     * Retrieves all owners with a specific status
     */
    @GetMapping("/stato/{stato}")
    public ResponseEntity<List<Proprietario>> getProprietariByStato(@PathVariable Stato stato) {
        List<Proprietario> proprietari = servicePropietario.getByStato(stato);
        return ResponseEntity.ok(proprietari);
    }

    /**
     * GET /api/proprietari/stati
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
     * POST /api/proprietari
     * Creates a new owner
     */
    @PostMapping
    public ResponseEntity<Proprietario> createProprietario(@RequestBody Proprietario proprietario) {
        Proprietario nuovoProprietario = servicePropietario.create(proprietario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoProprietario);
    }

    /**
     * PUT /api/proprietari/{id}
     * Updates an existing owner
     */
    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> updateProprietario(
            @PathVariable Integer id,
            @RequestBody Proprietario proprietario) {
        Proprietario proprietarioAggiornato = servicePropietario.update(id, proprietario);
        return ResponseEntity.ok(proprietarioAggiornato);
    }

    /**
     * DELETE /api/proprietari/{id}
     * Deletes an owner
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietario(@PathVariable Integer id) {
        servicePropietario.delete(id);
        return ResponseEntity.noContent().build();
    }
}

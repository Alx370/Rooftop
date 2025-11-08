package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Cliente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clienti")
public class ControllerCliente {

    @Autowired
    private ServiceCliente serviceCliente;

    /**
     * GET /api/clienti
     * Retrieves all clients
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClienti() {
        List<Cliente> clienti = serviceCliente.getAll();
        return ResponseEntity.ok(clienti);
    }

    /**
     * GET /api/clienti/{id}
     * Retrieves a client by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Cliente cliente = serviceCliente.getById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * GET /api/clienti/email/{email}
     * Retrieves a client by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        Cliente cliente = serviceCliente.getByEmail(email);
        return ResponseEntity.ok(cliente);
    }

    /**
     * POST /api/clienti
     * Creates a new client
     */
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente nuovo = serviceCliente.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    /**
     * PUT /api/clienti/{id}
     * Updates an existing client
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente aggiornato = serviceCliente.update(id, cliente);
        return ResponseEntity.ok(aggiornato);
    }

    /**
     * DELETE /api/clienti/{id}
     * Deletes a client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        serviceCliente.delete(id);
        return ResponseEntity.noContent().build();
    }
}

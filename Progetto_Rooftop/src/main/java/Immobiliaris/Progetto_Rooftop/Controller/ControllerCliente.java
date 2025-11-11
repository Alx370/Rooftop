package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Cliente;
import Immobiliaris.Progetto_Rooftop.Services.ServiceCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE', 'VALUTATORE')") // viewing all clients by AMMINISTRATORE or AGENTE
    public ResponseEntity<List<Cliente>> getAllClienti() {
        List<Cliente> clienti = serviceCliente.getAll();
        return ResponseEntity.ok(clienti);
    }

    /**
     * GET /api/clienti/{id}
     * Retrieves a client by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE', 'VALUTATORE')") // viewing client by AMMINISTRATORE or AGENTE
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Cliente cliente = serviceCliente.getById(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * GET /api/clienti/email/{email}
     * Retrieves a client by email
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE', 'VALUTATORE')") // viewing client by AMMINISTRATORE or AGENTE
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        Cliente cliente = serviceCliente.getByEmail(email);
        return ResponseEntity.ok(cliente);
    }

    /**
     * POST /api/clienti/registrati
     * Creates a new client
     */
    @PostMapping("/registrati")
    public ResponseEntity<Cliente> createClienteUtente(@RequestBody Cliente cliente) {
        Cliente nuovo = serviceCliente.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovo);
    }

    /**
     * PUT /api/clienti/{id}
     * Updates an existing client
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE')") // update client by AMMINISTRATORE or AGENTE
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente aggiornato = serviceCliente.update(id, cliente);
        return ResponseEntity.ok(aggiornato);
    }

    /**
     * DELETE /api/clienti/{id}
     * Deletes a client
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')") // delete client only by AMMINISTRATORE
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        serviceCliente.delete(id);
        return ResponseEntity.noContent().build();
    }
}

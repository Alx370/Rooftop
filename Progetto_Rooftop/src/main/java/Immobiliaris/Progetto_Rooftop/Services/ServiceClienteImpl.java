package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Cliente;
import Immobiliaris.Progetto_Rooftop.Repos.RepoCliente;

@Service
@Transactional
public class ServiceClienteImpl implements ServiceCliente {

    @Autowired
    private RepoCliente clienteRepo;

    public ServiceClienteImpl(RepoCliente clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @Override
    public Cliente create(Cliente cliente) {
        // Validate required fields
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome e richiesto");
        }
        if (cliente.getCognome() == null || cliente.getCognome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cognome e richiesto");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e richiesto");
        }

        // Normalize and check email uniqueness
        String email = normalizeEmail(cliente.getEmail());
        if (clienteRepo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata"); // 409 Conflict
        }
        cliente.setEmail(email);

        // Trim and set other fields
        cliente.setNome(cliente.getNome().trim());
        cliente.setCognome(cliente.getCognome().trim());
        if (cliente.getTelefono() != null) {
            cliente.setTelefono(cliente.getTelefono().trim());
        }

        // GDPR consent defaults to false if not set
        if (cliente.isConsenso_gdpr() == null) {
            cliente.setConsenso_gdpr(false);
        }
        if (cliente.isConsenso_marketing() == null) {
            cliente.setConsenso_marketing(false);
        }

        return clienteRepo.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAll() {
        return clienteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getById(int id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getByEmail(String email) {
        return clienteRepo.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente non trovato"));
    }

    @Override
    public Cliente update(int id, Cliente updated) {
        Cliente existing = getById(id);

        // Email uniqueness check if changed
        if (updated.getEmail() != null) {
            String newEmail = normalizeEmail(updated.getEmail());
            if (!newEmail.equalsIgnoreCase(existing.getEmail())
                    && clienteRepo.existsByEmail(newEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata");
            }
            existing.setEmail(newEmail);
        }

        // Update fields only if provided (null-safe)
        if (updated.getNome() != null) {
            existing.setNome(updated.getNome().trim());
        }
        if (updated.getCognome() != null) {
            existing.setCognome(updated.getCognome().trim());
        }
        if (updated.getTelefono() != null) {
            existing.setTelefono(updated.getTelefono().trim());
        }

        // Update GDPR/marketing consents
        existing.setConsenso_gdpr(updated.isConsenso_gdpr());
        existing.setConsenso_marketing(updated.isConsenso_marketing());

        return clienteRepo.save(existing);
    }

    @Override
    public void delete(int id) {
        if (!clienteRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente non trovato");
        }
        clienteRepo.deleteById(id);
    }

    //METHODS
    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    public RepoCliente getClienteRepo() {
        return clienteRepo;
    }

    public void setClienteRepo(RepoCliente clienteRepo) {
        this.clienteRepo = clienteRepo;
    }
}

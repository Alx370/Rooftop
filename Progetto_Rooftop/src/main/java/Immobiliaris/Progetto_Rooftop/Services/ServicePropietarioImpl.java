package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Proprietario;
import Immobiliaris.Progetto_Rooftop.Model.Stato;
import Immobiliaris.Progetto_Rooftop.Repos.RepoPropietario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ServicePropietarioImpl implements ServicePropietario {

    @Autowired
    private RepoPropietario proprietarioRepo;

    public ServicePropietarioImpl(RepoPropietario proprietarioRepo) {
        this.proprietarioRepo = proprietarioRepo;
    }

    @Override
    public Proprietario create(Proprietario proprietario) {
        // Validate required fields
        if (proprietario.getNome() == null || proprietario.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome e richiesto");
        }
        if (proprietario.getCognome() == null || proprietario.getCognome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cognome e richiesto");
        }
        if (proprietario.getEmail() == null || proprietario.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e richiesto");
        }

        // Normalize and check email uniqueness
        String email = normalizeEmail(proprietario.getEmail());
        if (proprietarioRepo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata"); // 409 Conflict
        }
        proprietario.setEmail(email);

        // Set default values if not provided
        if (proprietario.getStato() == null) {
            proprietario.setStato(Stato.ATTIVO); // Default state
        }

        // Trim and set other fields
        proprietario.setNome(proprietario.getNome().trim());
        proprietario.setCognome(proprietario.getCognome().trim());
        if (proprietario.getTelefono() != null) {
            proprietario.setTelefono(proprietario.getTelefono().trim());
        }

        // GDPR consent defaults to false if not set
        if (proprietario.isConsenso_gdpr() == null) {
            proprietario.setConsenso_gdpr(false);
        }
        if (proprietario.isConsenso_marketing() == null) {
            proprietario.setConsenso_marketing(false);
        }

        return proprietarioRepo.save(proprietario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proprietario> getAll() {
        return proprietarioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Proprietario getById(int id) {
        return proprietarioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proprietario non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public Proprietario getByEmail(String email) {
        return proprietarioRepo.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proprietario non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proprietario> getByStato(Stato stato) {
        return proprietarioRepo.findAllByStato(stato);
    }

    @Override
    public Proprietario update(int id, Proprietario updated) {
        Proprietario existing = getById(id);

        // Email uniqueness check if changed
        if (updated.getEmail() != null) {
            String newEmail = normalizeEmail(updated.getEmail());
            if (!newEmail.equalsIgnoreCase(existing.getEmail())
                    && proprietarioRepo.existsByEmail(newEmail)) {
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
        if (updated.getStato() != null) {
            existing.setStato(updated.getStato());
        }

        // boolean primitives can't be null, so we always update these
        existing.setConsenso_gdpr(updated.isConsenso_gdpr());
        existing.setConsenso_marketing(updated.isConsenso_marketing());

        return proprietarioRepo.save(existing);
    }

    @Override
    public void delete(int id) {
        if (!proprietarioRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proprietario non trovato");
        }
        proprietarioRepo.deleteById(id);
    }

    // METHODS
    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}

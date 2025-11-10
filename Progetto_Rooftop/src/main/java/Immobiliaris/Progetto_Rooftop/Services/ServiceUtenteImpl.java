package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Model.Ruolo;
import Immobiliaris.Progetto_Rooftop.Model.Stato;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Repos.RepoUtente;

@Service
@Transactional
public class ServiceUtenteImpl implements ServiceUtente {

    @Autowired
    private RepoUtente utenteRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ServiceUtenteImpl(RepoUtente utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    @Override
    public Utente create(Utente utente) {
        // Validate required fields
        if (utente.getNome() == null || utente.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome e richiesto");
        }
        if (utente.getCognome() == null || utente.getCognome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cognome e richiesto");
        }
        if (utente.getEmail() == null || utente.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e richiesto");
        }
        if (utente.getPassword() == null || utente.getPassword().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password e richiesta");
        }

        // Normalize and check email uniqueness
        String email = normalizeEmail(utente.getEmail());
        if (utenteRepo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata"); // 409 error Conflict
        }
        utente.setEmail(email);

        // Set default values if not provided
        if (utente.getRuolo() == null) {
            utente.setRuolo(Ruolo.AGENTE); // Default role
        }
        if (utente.getStato() == null) {
            utente.setStato(Stato.ATTIVO); // Default state
        }

        // Trim and set other fields
        utente.setNome(utente.getNome().trim());
        utente.setCognome(utente.getCognome().trim());
        if (utente.getTelefono() != null) {
            utente.setTelefono(utente.getTelefono().trim());
        }

        // Hash password before saving, 
        String hashedPassword = passwordEncoder.encode(utente.getPassword().trim());
        utente.setPassword(hashedPassword);
        
        return utenteRepo.save(utente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utente> getAll() {
        return utenteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Utente getById(Integer id) {
        return utenteRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public Utente getByEmail(String email) {
        return utenteRepo.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utente> getByRuolo(Ruolo ruolo) {
        return utenteRepo.findAllByRuolo(ruolo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utente> getByStato(Stato stato) {
        return utenteRepo.findAllByStato(stato);
    }

    // this method updates user details except password
    @Override
    public Utente update(Integer id, Utente updated) {
        Utente existing = getById(id);

        // email uniqueness check if changed
        if (updated.getEmail() != null) {
            String newEmail = normalizeEmail(updated.getEmail());
            if (!newEmail.equalsIgnoreCase(existing.getEmail())
                    && utenteRepo.existsByEmail(newEmail)) {
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
        if (updated.getRuolo() != null) {
            existing.setRuolo(updated.getRuolo());
        }

        // Update password if provided
        if (updated.getPassword() != null && !updated.getPassword().trim().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(updated.getPassword().trim());
            existing.setPassword(hashedPassword);
        }

        return utenteRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!utenteRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }
        utenteRepo.deleteById(id);
    }

    // METHODS 
    // method to Help to normalize email if needed
    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
    
    /**
     * Updates the password of the user with the specified ID.
     * The new password is hashed before being saved.
     * Only in the update method for the user.
     */
    @Override
    public Utente updatePassword(Integer id, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password è richiesta");
        }
        
        Utente user = getById(id);
        String hashedPassword = passwordEncoder.encode(newPassword.trim());
        user.setPassword(hashedPassword);
        
        return utenteRepo.save(user);
    }

    public RepoUtente getUtenteRepo() {
        return utenteRepo;
    }

    public void setUtenteRepo(RepoUtente utenteRepo) {
        this.utenteRepo = utenteRepo;
    }
}
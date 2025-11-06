package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Ruolo;
import Immobiliaris.Progetto_Rooftop.Model.Stato;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Repos.RepoUtente;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ServiceUtenteImpl implements ServiceUtente {

    private final RepoUtente utenteRepo;

    public ServiceUtenteImpl(RepoUtente utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    @Override
    public Utente create(Utente utente) {
        String email = normalizeEmail(utente.getEmail());
        if (utenteRepo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        utente.setEmail(email);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Utente getByEmail(String email) {
        return utenteRepo.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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

    @Override
    public Utente update(Integer id, Utente updated) {
        Utente existing = getById(id);

        // email uniqueness check if changed
        if (updated.getEmail() != null) {
            String newEmail = normalizeEmail(updated.getEmail());
            if (!newEmail.equalsIgnoreCase(existing.getEmail())
                    && utenteRepo.existsByEmail(newEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
            }
            existing.setEmail(newEmail);
        }

        existing.setNome(updated.getNome());
        existing.setCognome(updated.getCognome());
        existing.setTelefono(updated.getTelefono());
        existing.setStato(updated.getStato());
        existing.setRuolo(updated.getRuolo());

        // TODO : password update dedicated with hashing
        return utenteRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!utenteRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        utenteRepo.deleteById(id);
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
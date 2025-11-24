package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Enum.Ruolo;
import Immobiliaris.Progetto_Rooftop.Enum.Stato;
import Immobiliaris.Progetto_Rooftop.Model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RepoUtente extends JpaRepository<Utente, Integer> {

    // Find a user by email (case-insensitive)
    Optional<Utente> findByEmail(String email);

    // Check if an email already exists
    boolean existsByEmail(String email);

    // Get all users by role
    List<Utente> findAllByRuolo(Ruolo ruolo);

    // Get all users by status
    List<Utente> findAllByStato(Stato stato);
}
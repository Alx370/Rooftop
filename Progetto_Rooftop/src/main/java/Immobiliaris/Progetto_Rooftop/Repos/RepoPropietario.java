package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Proprietario;
import Immobiliaris.Progetto_Rooftop.Model.Stato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RepoPropietario extends JpaRepository<Proprietario, Integer> {

    // Find an owner by email (case-insensitive)
    Optional<Proprietario> findByEmailIgnoreCase(String email);

    // Check if an email already exists
    boolean existsByEmailIgnoreCase(String email);

    // Get all owners by status
    List<Proprietario> findAllByStato(Stato stato);

}

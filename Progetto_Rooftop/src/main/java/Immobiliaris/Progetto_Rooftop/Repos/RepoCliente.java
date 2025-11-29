package Immobiliaris.Progetto_Rooftop.Repos;

import Immobiliaris.Progetto_Rooftop.Model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RepoCliente extends JpaRepository<Cliente, Integer> {

    // Find a client by email (case-insensitive)
    Optional<Cliente> findByEmail(String email);

    // Check if an email already exists
    boolean existsByEmail(String email);
}
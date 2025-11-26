package Immobiliaris.Progetto_Rooftop.Mail;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepoNewsletter extends JpaRepository<Newsletter, Integer> {
    
    // Find a subscriber by email
    Optional<Newsletter> findByEmail(String email);
    
    // Check if an email already exists
    boolean existsByEmail(String email);
}

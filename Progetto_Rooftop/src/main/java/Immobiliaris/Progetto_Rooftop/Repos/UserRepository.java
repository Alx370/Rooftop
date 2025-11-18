package Immobiliaris.Progetto_Rooftop.Repository;

import Immobiliaris.Progetto_Rooftop.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

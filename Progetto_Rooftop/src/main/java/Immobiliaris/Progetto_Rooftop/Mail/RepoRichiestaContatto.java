package Immobiliaris.Progetto_Rooftop.Mail;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepoRichiestaContatto extends JpaRepository<RichiestaContatto, Integer> {
    
    // Find all requests with a given status (e.g. NUOVA, LETTA, COMPLETATA)
    List<RichiestaContatto> findByStato(RichiestaContatto.StatoRichiesta stato);
    
    // Find all requests saved as FAQ
    List<RichiestaContatto> findBySalvataComeFaq(Boolean salvata);
}

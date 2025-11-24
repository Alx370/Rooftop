package Immobiliaris.Progetto_Rooftop.Services;
import Immobiliaris.Progetto_Rooftop.Enum.Ruolo;
import Immobiliaris.Progetto_Rooftop.Enum.Stato;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceUtente {
    
    Utente create(Utente utente);

    List<Utente> getAll();

    Utente getById(Integer id);

    Utente getByEmail(String email);

    List<Utente> getByRuolo(Ruolo ruolo);

    List<Utente> getByStato(Stato stato);

    Utente update(Integer id, Utente updated);

    Utente updatePassword(Integer id, String newPassword);

    void delete(Integer id);
}

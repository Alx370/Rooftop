package Immobiliaris.Progetto_Rooftop.Services;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceUtente {
    void AddUtente(Utente utente);
    Utente FindById(int id);
    List<Utente> FindAll();
    void UpdateUtente(Utente utente);
}

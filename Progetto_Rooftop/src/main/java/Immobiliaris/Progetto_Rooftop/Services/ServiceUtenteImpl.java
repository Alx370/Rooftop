package Immobiliaris.Progetto_Rooftop.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Repos.RepoUtente;

@Service
public class ServiceUtenteImpl implements ServiceUtente {

    @Autowired
    private RepoUtente repoUtente;

    @Override
    public void AddUtente(Utente utente) {
        repoUtente.save(utente);
    }

    @Override
    public Utente FindById(int id) {
        return repoUtente.findById(id).orElse(null);
    }

    @Override
    public java.util.List<Utente> FindAll() {
        return repoUtente.findAll();
    }

    @Override
    public void UpdateUtente(Utente utente) {
        repoUtente.save(utente);
    }
}
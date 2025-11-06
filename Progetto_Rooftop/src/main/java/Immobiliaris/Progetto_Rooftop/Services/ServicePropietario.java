package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Proprietario;
import Immobiliaris.Progetto_Rooftop.Model.Stato;

import java.util.List;

public interface ServicePropietario {

    Proprietario create(Proprietario proprietario);

    List<Proprietario> getAll();

    Proprietario getById(int id);

    Proprietario getByEmail(String email);

    List<Proprietario> getByStato(Stato stato);

    Proprietario update(int id, Proprietario updated);

    void delete(int id);
}
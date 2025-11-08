package Immobiliaris.Progetto_Rooftop.Services;

import Immobiliaris.Progetto_Rooftop.Model.Cliente;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceCliente {

    Cliente create(Cliente cliente);

    List<Cliente> getAll();

    Cliente getById(int id);

    Cliente getByEmail(String email);

    Cliente update(int id, Cliente updated);

    void delete(int id);
}

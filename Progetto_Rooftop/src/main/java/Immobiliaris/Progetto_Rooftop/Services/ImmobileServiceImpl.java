package Immobiliaris.Progetto_Rooftop.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.StatoAnnuncio;
import Immobiliaris.Progetto_Rooftop.Model.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Repos.RepoImmobili;

@Service
public class ImmobileServiceImpl implements ServiceImmobile {

    @Autowired
    private RepoImmobili repoImmobili;

    @Override
    public Immobile create(Immobile immobile) {
        if (immobile.getCreatoIl() == null) {
            immobile.setCreatoIl(LocalDateTime.now());
        }
        if (immobile.getStatoAnnuncio() == null) {
            immobile.setStatoAnnuncio(StatoAnnuncio.VALUTAZIONE);
        }
        if (immobile.getStatoImmobile() == null) {
            immobile.setStatoImmobile(StatoImmobile.BUONO);
        }
        return repoImmobili.save(immobile);
    }

    @Override
    public List<Immobile> getAll() {
        return repoImmobili.findAll();
    }

    @Override
    public Immobile getById(int id) {
        return repoImmobili.findById(id)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato con id: " + id));
    }

    @Override
    public Immobile update(int id, Immobile updated) {
        Immobile immobile = repoImmobili.findById(id)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato con id: " + id));
        
        immobile.setIdProprietario(updated.getIdProprietario());
        immobile.setIdAgente(updated.getIdAgente());
        immobile.setTitolo(updated.getTitolo());
        immobile.setDescrizione(updated.getDescrizione());
        immobile.setIndirizzo(updated.getIndirizzo());
        immobile.setCivico(updated.getCivico());
        immobile.setCitta(updated.getCitta());
        immobile.setProvincia(updated.getProvincia());
        immobile.setCap(updated.getCap());
        immobile.setQuartiere(updated.getQuartiere());
        immobile.setTipologia(updated.getTipologia());
        immobile.setMetriQuadri(updated.getMetriQuadri());
        immobile.setPiano(updated.getPiano());
        immobile.setAnnoCostruzione(updated.getAnnoCostruzione());
        immobile.setPrezzoRichiesto(updated.getPrezzoRichiesto());
        immobile.setStatoImmobile(updated.getStatoImmobile());
        immobile.setStatoAnnuncio(updated.getStatoAnnuncio());
        
        return repoImmobili.save(immobile);
    }

    @Override
    public void delete(int id) {
        if (!repoImmobili.existsById(id)) {
            throw new RuntimeException("Immobile non trovato con id: " + id);
        }
        repoImmobili.deleteById(id);
    }

}
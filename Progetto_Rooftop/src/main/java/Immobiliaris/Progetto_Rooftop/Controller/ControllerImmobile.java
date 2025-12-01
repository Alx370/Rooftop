package Immobiliaris.Progetto_Rooftop.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaAbitazione;
import Immobiliaris.Progetto_Rooftop.Enum.ClasseEnergetica;
import Immobiliaris.Progetto_Rooftop.Enum.Orientamento;
import Immobiliaris.Progetto_Rooftop.Enum.Riscaldamento;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Enum.Tipologia;
import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Model.Valutazione;
import Immobiliaris.Progetto_Rooftop.Services.ServiceImmobile;
import Immobiliaris.Progetto_Rooftop.Services.ServiceUtente;

@RestController
@RequestMapping("/api/immobili")
/**
 * Controller REST per la gestione degli immobili.
 * Espone endpoint CRUD e operazioni di valutazione.
 * Base path: `/api/immobili`.
 */
public class ControllerImmobile {

    @Autowired
    private ServiceImmobile serviceImmobile;

    @Autowired
    private ServiceUtente serviceUtente;

    @GetMapping
    /**
     * Restituisce tutti gli immobili.
     * Risponde 200 con la lista oppure 204 se vuota.
     */
    public ResponseEntity<List<Immobile>> getAllImmobili() {
        List<Immobile> immobili = serviceImmobile.getAll();
        if (immobili.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(immobili);
    }

    @PostMapping("/{id}/valutazione/automatica")
    /**
     * Calcola una valutazione automatica basata sul prezzo al mq della zona.
     * Richiede nel body `prezzoMqZona`.
     * @param id id dell'immobile
     * @return 200 con la valutazione calcolata
     */
    public ResponseEntity<Valutazione> stimaAutomatica(@PathVariable Integer id, @RequestBody Map<String, BigDecimal> payload) {
        BigDecimal prezzoMqZona = payload.get("prezzoMqZona");
        if (prezzoMqZona == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prezzoMqZona mancante");
        }
        Valutazione v = serviceImmobile.stimaAutomatica(id, prezzoMqZona);
        return ResponseEntity.ok(v);
    }

    @PostMapping("/{id}/valutazione/automatica/omi")
    /**
     * Calcola una valutazione automatica usando i dati OMI della zona.
     * @param id id dell'immobile
     * @return 200 con la valutazione stimata
     */
    public ResponseEntity<Valutazione> stimaAutomaticaDaOmi(@PathVariable Integer id) {
        Valutazione v = serviceImmobile.stimaAutomaticaDaOMI(id);
        return ResponseEntity.ok(v);
    }

    @PostMapping("/{id}/valutazione/manuale")
    /**
     * Crea una valutazione manuale associando opzionalmente un valutatore.
     * Body: `prezzoMqZona` (string), `idValutatore` (string opzionale).
     * @param id id dell'immobile
     * @return 201 con la valutazione creata
     */
    public ResponseEntity<Valutazione> creaValutazioneManuale(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        String prezzoStr = payload.get("prezzoMqZona");
        String idValStr = payload.get("idValutatore");
        if (prezzoStr == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prezzoMqZona mancante");
        }
        BigDecimal prezzo = new BigDecimal(prezzoStr);
        Integer idVal = idValStr != null ? Integer.valueOf(idValStr) : null;
        Valutazione v = serviceImmobile.creaValutazioneManuale(id, idVal, prezzo);
        return ResponseEntity.status(HttpStatus.CREATED).body(v);
    }

    @PostMapping("/valutazione/automatica/omi/indirizzo")
    /**
     * Calcola la valutazione automatica a partire da un indirizzo e caratteristiche.
     * Campi obbligatori: `provincia`, `citta`, `indirizzo`, `metri_quadri`, `tipologia`.
     * @return 200 con la valutazione stimata; 400 se campi mancanti
     */
    public ResponseEntity<Valutazione> stimaAutomaticaDaIndirizzo(@RequestBody Map<String, String> payload) {
        String provincia = payload.get("provincia");
        String citta = payload.get("citta");
        String indirizzo = payload.get("indirizzo");
        String civico = payload.get("civico");
        BigDecimal mq = payload.get("metri_quadri") != null ? new BigDecimal(payload.get("metri_quadri")) : null;
        Tipologia tipologia = payload.get("tipologia") != null ? Tipologia.valueOf(payload.get("tipologia")) : null;
        CategoriaAbitazione categoria = payload.get("categoria_abitazione") != null ? CategoriaAbitazione.valueOf(payload.get("categoria_abitazione")) : null;
        StatoImmobile statoImmobile = payload.get("stato_immobile") != null ? StatoImmobile.valueOf(payload.get("stato_immobile")) : StatoImmobile.BUONO;
        String piano = payload.get("piano");
        Integer bagni = payload.get("bagni") != null ? Integer.valueOf(payload.get("bagni")) : null;

        CaratteristicheImmobile c = new CaratteristicheImmobile();
        if (payload.get("ascensore") != null) c.setAscensore(Boolean.valueOf(payload.get("ascensore")));
        if (payload.get("parcheggio") != null) c.setParcheggio(Boolean.valueOf(payload.get("parcheggio")));
        if (payload.get("posti_auto") != null) c.setPosti_auto(Integer.valueOf(payload.get("posti_auto")));
        if (payload.get("garage") != null) c.setGarage(Boolean.valueOf(payload.get("garage")));
        if (payload.get("balcone_mq") != null) c.setBalcone_mq(new BigDecimal(payload.get("balcone_mq")));
        if (payload.get("terrazzo_mq") != null) c.setTerrazzo_mq(new BigDecimal(payload.get("terrazzo_mq")));
        if (payload.get("giardino_mq") != null) c.setGiardino_mq(new BigDecimal(payload.get("giardino_mq")));
        if (payload.get("cantina") != null) c.setCantina(Boolean.valueOf(payload.get("cantina")));
        if (payload.get("arredato") != null) c.setArredato(Boolean.valueOf(payload.get("arredato")));
        if (payload.get("aria_condizionata") != null) c.setAria_condizionata(Boolean.valueOf(payload.get("aria_condizionata")));
        if (payload.get("allarme") != null) c.setAllarme(Boolean.valueOf(payload.get("allarme")));
        if (payload.get("riscaldamento") != null) c.setRiscaldamento(Riscaldamento.valueOf(payload.get("riscaldamento")));
        if (payload.get("classe_energetica") != null) c.setClasse_energetica(ClasseEnergetica.valueOf(payload.get("classe_energetica")));
        if (payload.get("orientamento") != null) c.setOrientamento(Orientamento.valueOf(payload.get("orientamento")));

        if (provincia == null || citta == null || indirizzo == null || mq == null || tipologia == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "campi obbligatori mancanti");
        }
        Valutazione v = serviceImmobile.stimaAutomaticaDaIndirizzo(provincia, citta, indirizzo, civico, mq, tipologia, categoria, statoImmobile, piano, bagni, c);
        return ResponseEntity.ok(v);
    }

    @GetMapping("/{id}")
    /**
     * Restituisce l'immobile per id.
     * @param id id dell'immobile
     * @return 200 con l'immobile o 404 se non trovato
     */
    public ResponseEntity<?> getImmobileById(@PathVariable Integer id) {
        try {
            Immobile immobile = serviceImmobile.getById(id);
            return ResponseEntity.ok(immobile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    /**
     * Crea un immobile associando il proprietario dall'utente autenticato.
     * Permesso solo al ruolo `PROPRIETARIO`.
     * @return 201 con l'immobile creato; 401/403/400 in caso di errori
     */
    public ResponseEntity<?> createImmobile(@RequestBody Immobile immobile) {
        try {
            // Retrieve current authentication
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String userId = auth.getPrincipal().toString();
                Utente utente = serviceUtente.getById(Integer.valueOf(userId));
                
                // if the user is logged in as a PROPRIETARIO, find their proprietario profile
                if ("PROPRIETARIO".equals(utente.getRuolo().name())) {
                    immobile.setProprietario(utente);
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
                        "Solo i proprietari possono creare immobili");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
                    "Autenticazione richiesta per creare un immobile");
            }
            
            Immobile nuovoImmobile = serviceImmobile.create(immobile);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoImmobile); 
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    /**
     * Aggiorna un immobile esistente con i campi forniti.
     * @param id id dell'immobile
     * @return 200 con l'immobile aggiornato o 404 se non trovato
     */
    public ResponseEntity<?> updateImmobile(@PathVariable Integer id, @RequestBody Immobile immobile) {
        try {
            Immobile aggiornato = serviceImmobile.update(id, immobile);
            return ResponseEntity.ok(aggiornato); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    /**
     * Elimina l'immobile per id.
     * @param id id dell'immobile
     * @return 204 se eliminato; 404 se non trovato
     */
    public ResponseEntity<?> deleteImmobile(@PathVariable Integer id) {
        try {
            serviceImmobile.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

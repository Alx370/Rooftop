package Immobiliaris.Progetto_Rooftop.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaAbitazione;
import Immobiliaris.Progetto_Rooftop.Enum.MetodoValutazione;
import Immobiliaris.Progetto_Rooftop.Enum.StatoAnnuncio;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;
import Immobiliaris.Progetto_Rooftop.Enum.StatoValutazione;
import Immobiliaris.Progetto_Rooftop.Enum.Tipologia;
import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import Immobiliaris.Progetto_Rooftop.Model.Immobile;
import Immobiliaris.Progetto_Rooftop.Model.Utente;
import Immobiliaris.Progetto_Rooftop.Model.Valutazione;
import Immobiliaris.Progetto_Rooftop.Repos.RepoCaratteristicheImmobile;
import Immobiliaris.Progetto_Rooftop.Repos.RepoImmobili;
import Immobiliaris.Progetto_Rooftop.Repos.RepoValutazioni;

/**
 * Implementazione del servizio per la gestione degli Immobili.
 * Esegue validazioni, imposta valori di default e delega le operazioni al repository.
 */
@Service
@Transactional
public class ServiceImmobileImpl implements ServiceImmobile {

    @Autowired
    private RepoImmobili repoImmobili;

    @Autowired
    private RepoValutazioni repoValutazioni;

    @Autowired
    private RepoCaratteristicheImmobile repoCaratteristicheImmobile;

    @Autowired
    private ServiceOmi serviceOmi;

    @Autowired
    private ServiceNominatim serviceNominatim;

    @Autowired
    private ServiceUtente serviceUtente;

    public ServiceImmobileImpl(RepoImmobili repoImmobili) {
        this.repoImmobili = repoImmobili;
    }

    /** Valida i campi obbligatori dell'immobile. */
    /**
     * Valida i campi obbligatori dell'immobile prima della persistenza.
     * Lancia eccezioni HTTP 400 in caso di valori mancanti o non validi.
     * @param immobile entità da validare
     */
    private void validate(Immobile immobile) {
        if (immobile.getProprietario() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proprietario e richiesto");
        }
        if (immobile.getTitolo() == null || immobile.getTitolo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titolo e richiesto");
        }
        if (immobile.getIndirizzo() == null || immobile.getIndirizzo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indirizzo e richiesto");
        }
        if (immobile.getCitta() == null || immobile.getCitta().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citta e richiesta");
        }
        if (immobile.getProvincia() == null || immobile.getProvincia().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provincia e richiesta");
        }
        if (immobile.getCap() == null || immobile.getCap().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP e richiesto");
        }
        if (immobile.getTipologia() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipologia e richiesta");
        }
        if (immobile.getMetri_quadri() == null || immobile.getMetri_quadri().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metri quadri devono essere maggiori di zero");
        }
        if (immobile.getPrezzo_richiesto() == null || immobile.getPrezzo_richiesto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo richiesto deve essere maggiore di zero");
        }
    }

    @Override
    /**
     * Crea un immobile dopo validazione e impostazione dei default.
     * Inizializza stato annuncio e timestamp di creazione.
     * @param immobile entità da salvare
     * @return immobile persistito
     */
    public Immobile create(Immobile immobile) {
        validate(immobile);
        
        // Set default values if not provided
        if (immobile.getCreato_il() == null) {
            immobile.setCreato_il(LocalDateTime.now());
        }
        if (immobile.getStato_annuncio() == null) {
            immobile.setStato_annuncio(StatoAnnuncio.VALUTAZIONE);
        }
        if (immobile.getStato_immobile() == null) {
            immobile.setStato_immobile(StatoImmobile.BUONO);
        }

        // Normalize text fields
        immobile.setTitolo(immobile.getTitolo().trim());
        immobile.setIndirizzo(immobile.getIndirizzo().trim());
        immobile.setCitta(immobile.getCitta().trim());
        immobile.setProvincia(immobile.getProvincia().trim().toUpperCase()); // Province codes are uppercase
        immobile.setCap(immobile.getCap().trim());
        
        if (immobile.getDescrizione() != null) {
            immobile.setDescrizione(immobile.getDescrizione().trim());
        }
        if (immobile.getCivico() != null) {
            immobile.setCivico(immobile.getCivico().trim());
        }
        if (immobile.getQuartiere() != null) {
            immobile.setQuartiere(immobile.getQuartiere().trim());
        }
        if (immobile.getPiano() != null) {
            immobile.setPiano(immobile.getPiano().trim());
        }

        return repoImmobili.save(immobile);
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * Restituisce tutti gli immobili dal repository.
     * @return lista di immobili
     */
    public List<Immobile> getAll() {
        return repoImmobili.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * Recupera un immobile per id, lanciando 404 se non esiste.
     * @param id identificativo immobile
     * @return immobile trovato
     */
    public Immobile getById(int id) {
        return repoImmobili.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Immobile non trovato"));
    }

    @Override
    /**
     * Aggiorna i campi non null dell'immobile esistente.
     * Applica normalizzazioni su stringhe e validazioni di base.
     * @param id identificativo immobile
     * @param updated campi da aggiornare
     * @return immobile aggiornato
     */
    public Immobile update(int id, Immobile updated) {
        Immobile existing = getById(id);

        // Update only non-null fields
        if (updated.getProprietario() != null) {
            existing.setProprietario(updated.getProprietario());
        }
        if (updated.getId_agente() != null) {
            existing.setId_agente(updated.getId_agente());
        }
        if (updated.getTitolo() != null && !updated.getTitolo().trim().isEmpty()) {
            existing.setTitolo(updated.getTitolo().trim());
        }
        if (updated.getDescrizione() != null) {
            existing.setDescrizione(updated.getDescrizione().trim());
        }
        if (updated.getIndirizzo() != null && !updated.getIndirizzo().trim().isEmpty()) {
            existing.setIndirizzo(updated.getIndirizzo().trim());
        }
        if (updated.getCivico() != null) {
            existing.setCivico(updated.getCivico().trim());
        }
        if (updated.getCitta() != null && !updated.getCitta().trim().isEmpty()) {
            existing.setCitta(updated.getCitta().trim());
        }
        if (updated.getProvincia() != null && !updated.getProvincia().trim().isEmpty()) {
            existing.setProvincia(updated.getProvincia().trim().toUpperCase());
        }
        if (updated.getCap() != null && !updated.getCap().trim().isEmpty()) {
            existing.setCap(updated.getCap().trim());
        }
        if (updated.getQuartiere() != null) {
            existing.setQuartiere(updated.getQuartiere().trim());
        }
        if (updated.getTipologia() != null) {
            existing.setTipologia(updated.getTipologia());
        }
        if (updated.getMetri_quadri() != null && updated.getMetri_quadri().compareTo(java.math.BigDecimal.ZERO) > 0) {
            existing.setMetri_quadri(updated.getMetri_quadri());
        }
        if (updated.getPiano() != null) {
            existing.setPiano(updated.getPiano().trim());
        }
        if (updated.getAnno_costruzione() != null) {
            existing.setAnno_costruzione(updated.getAnno_costruzione());
        }
        if (updated.getPrezzo_richiesto() != null && updated.getPrezzo_richiesto().compareTo(java.math.BigDecimal.ZERO) > 0) {
            existing.setPrezzo_richiesto(updated.getPrezzo_richiesto());
        }
        if (updated.getStato_immobile() != null) {
            existing.setStato_immobile(updated.getStato_immobile());
        }
        if (updated.getStato_annuncio() != null) {
            existing.setStato_annuncio(updated.getStato_annuncio());
        }

        return repoImmobili.save(existing);
    }

    @Override
    /**
     * Elimina l'immobile se esiste, altrimenti lancia 404.
     * @param id identificativo immobile
     */
    public void delete(int id) {
        if (!repoImmobili.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Immobile non trovato");
        }
        repoImmobili.deleteById(id);
    }

    @Override
    /**
     * Calcola la valutazione automatica usando il prezzo al mq della zona.
     * Applica moltiplicatori e add-on basati sulle caratteristiche.
     * @param idImmobile id immobile
     * @param prezzoMqZona prezzo al mq della zona
     * @return valutazione stimata non persistita
     */
    public Valutazione stimaAutomatica(Integer idImmobile, BigDecimal prezzoMqZona) {
        if (prezzoMqZona == null || prezzoMqZona.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo mq zona non valido");
        }
        Immobile immobile = getById(idImmobile);
        CaratteristicheImmobile c = repoCaratteristicheImmobile.findByImmobile_Id_immobile(idImmobile).orElse(null);
        return calcolaValutazione(immobile, c, prezzoMqZona);
    }

    @Override
    /**
     * Calcola la valutazione automatica utilizzando i valori OMI della zona.
     * Recupera l'immobile e le sue caratteristiche, poi applica la stima.
     * @param idImmobile id immobile
     * @return valutazione stimata non persistita
     */
    public Valutazione stimaAutomaticaDaOMI(Integer idImmobile) {
        Immobile immobile = getById(idImmobile);
        String statoPreferito = immobile.getStato_immobile() == StatoImmobile.NUOVO ? "OTTIMO" : "NORMALE";
        BigDecimal codiceTipologia = null;
        if (immobile.getCategoria_abitazione() != null) {
            if (null != immobile.getCategoria_abitazione()) switch (immobile.getCategoria_abitazione()) {
                case SIGNORILE -> codiceTipologia = BigDecimal.valueOf(19.00);
                case CIVILE -> codiceTipologia = BigDecimal.valueOf(20.00);
                case POPOLARE -> codiceTipologia = BigDecimal.valueOf(21.00);
                default -> {
                }
            }
        }
        BigDecimal prezzo = null;
        String quartiereLookup = immobile.getQuartiere();
        if (quartiereLookup == null || quartiereLookup.isBlank()) {
            String q = serviceNominatim.resolveQuartiere(immobile.getProvincia(), immobile.getCitta(), immobile.getIndirizzo(), immobile.getCivico());
            if (q != null && !q.isBlank()) {
                quartiereLookup = q;
            }
        }
        if (quartiereLookup != null && !quartiereLookup.isBlank()) {
            var opt = (codiceTipologia != null)
                    ? serviceOmi.findPrezziByQuartiereTipologia(immobile.getProvincia(), immobile.getCitta(), quartiereLookup, statoPreferito, codiceTipologia)
                    : serviceOmi.findPrezziByQuartiere(immobile.getProvincia(), immobile.getCitta(), quartiereLookup, statoPreferito);
            if (opt.isPresent()) {
                var p = opt.get();
                if (null == immobile.getStato_immobile()) prezzo = p.min.add(p.max).divide(BigDecimal.valueOf(2));
                else prezzo = switch (immobile.getStato_immobile()) {
                    case NUOVO -> p.max;
                    case DA_RISTRUTTUARE -> p.min;
                    default -> p.min.add(p.max).divide(BigDecimal.valueOf(2));
                };
                Valutazione v = calcolaValutazione(immobile, repoCaratteristicheImmobile.findByImmobile_Id_immobile(idImmobile).orElse(null), prezzo);
                v.setNote("Zona OMI: " + p.zona);
                return v;
            }
        }
        var avgOpt = (codiceTipologia != null)
                ? serviceOmi.findPrezzoMedioByComuneTipologia(immobile.getProvincia(), immobile.getCitta(), statoPreferito, codiceTipologia)
                : serviceOmi.findPrezzoMedioByComune(immobile.getProvincia(), immobile.getCitta(), statoPreferito);
        if (avgOpt.isPresent()) {
            prezzo = avgOpt.get();
            Valutazione v = calcolaValutazione(immobile, repoCaratteristicheImmobile.findByImmobile_Id_immobile(idImmobile).orElse(null), prezzo);
            return v;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo OMI non disponibile");
    }

    /**
     * Calcola la valutazione applicando moltiplicatori e add-on alle superfici.
     * Tiene conto di tipologia, stato, piano e caratteristiche accessorie.
     * @param immobile immobile oggetto della stima
     * @param c caratteristiche aggiuntive (può essere null)
     * @param prezzoMqZona prezzo al mq della zona
     * @return valutazione calcolata non persistita
     */
    private Valutazione calcolaValutazione(Immobile immobile, CaratteristicheImmobile c, BigDecimal prezzoMqZona) {
        BigDecimal base = immobile.getMetri_quadri().multiply(prezzoMqZona);
        BigDecimal multiplier = BigDecimal.ONE;

        if (immobile.getTipologia() != null) {
            switch (immobile.getTipologia()) {
                case ATTICO -> multiplier = multiplier.multiply(BigDecimal.valueOf(1.03));
                case VILLA -> multiplier = multiplier.multiply(BigDecimal.valueOf(1.02));
                case MONOLOCALE -> multiplier = multiplier.multiply(BigDecimal.valueOf(0.98));
                default -> multiplier = multiplier.multiply(BigDecimal.ONE);
            }
        }
        if (immobile.getBagni() != null && immobile.getBagni() > 1) {
            double extra = Math.min(immobile.getBagni() - 1, 3);
            multiplier = multiplier.multiply(BigDecimal.valueOf(1 + (0.02 * extra)));
        }
        if (c != null) {
            if (c.getAscensore() != null && c.getAscensore()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.02));
            } else {
                if (immobile.getPiano() != null) {
                    int pianoNum = parseFloor(immobile.getPiano());
                    if (pianoNum >= 3) {
                        multiplier = multiplier.multiply(BigDecimal.valueOf(0.97));
                    }
                }
            }
            if (c.getParcheggio() != null && c.getParcheggio()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.01));
            }
            if (c.getPosti_auto() != null && c.getPosti_auto() > 0) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1 + (0.01 * c.getPosti_auto())));
            }
            if (c.getGarage() != null && c.getGarage()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.03));
            }
            if (c.getCantina() != null && c.getCantina()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.015));
            }
            if (c.getArredato() != null && c.getArredato()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.01));
            }
            if (c.getAria_condizionata() != null && c.getAria_condizionata()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.008));
            }
            if (c.getAllarme() != null && c.getAllarme()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.006));
            }
            if (c.getIndipendente() != null && c.getIndipendente()) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(1.03));
            }
            if (c.getRiscaldamento() != null) {
                BigDecimal rMult = switch (c.getRiscaldamento()) {
                    case AUTONOMO -> BigDecimal.valueOf(1.02);
                    case CENTRALIZZATO -> BigDecimal.ONE;
                    case ASSENTE -> BigDecimal.valueOf(0.90);
                };
                multiplier = multiplier.multiply(rMult);
            }
            if (c.getClasse_energetica() != null) {
                BigDecimal eMult = switch (c.getClasse_energetica()) {
                    case A4 -> BigDecimal.valueOf(1.05);
                    case A3 -> BigDecimal.valueOf(1.04);
                    case A2 -> BigDecimal.valueOf(1.03);
                    case A1 -> BigDecimal.valueOf(1.02);
                    case B -> BigDecimal.valueOf(1.01);
                    case C -> BigDecimal.ONE;
                    case D -> BigDecimal.valueOf(0.99);
                    case E -> BigDecimal.valueOf(0.97);
                    case F -> BigDecimal.valueOf(0.95);
                    case G -> BigDecimal.valueOf(0.92);
                    case NC -> BigDecimal.valueOf(0.90);
                };
                multiplier = multiplier.multiply(eMult);
            }
            if (c.getOrientamento() != null) {
                BigDecimal oMult = switch (c.getOrientamento()) {
                    case SUD -> BigDecimal.valueOf(1.02);
                    case SUD_EST, SUD_OVEST -> BigDecimal.valueOf(1.015);
                    case EST, OVEST -> BigDecimal.valueOf(1.01);
                    case NORD_EST, NORD_OVEST -> BigDecimal.valueOf(0.995);
                    case NORD -> BigDecimal.valueOf(0.98);
                };
                multiplier = multiplier.multiply(oMult);
            }
        }
        BigDecimal addons = BigDecimal.ZERO;
        if (c != null) {
            if (c.getTerrazzo_mq() != null && c.getTerrazzo_mq().compareTo(BigDecimal.ZERO) > 0) {
                addons = addons.add(c.getTerrazzo_mq().multiply(prezzoMqZona).multiply(BigDecimal.valueOf(0.40)));
            }
            if (c.getBalcone_mq() != null && c.getBalcone_mq().compareTo(BigDecimal.ZERO) > 0) {
                addons = addons.add(c.getBalcone_mq().multiply(prezzoMqZona).multiply(BigDecimal.valueOf(0.20)));
            }
            if (c.getGiardino_mq() != null && c.getGiardino_mq().compareTo(BigDecimal.ZERO) > 0) {
                addons = addons.add(c.getGiardino_mq().multiply(prezzoMqZona).multiply(BigDecimal.valueOf(0.20)));
            }
        }
        BigDecimal estimated = base.multiply(multiplier).add(addons).setScale(2, RoundingMode.HALF_UP);
        BigDecimal margin = BigDecimal.valueOf(0.07);
        BigDecimal min = estimated.multiply(BigDecimal.ONE.subtract(margin)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal max = estimated.multiply(BigDecimal.ONE.add(margin)).setScale(2, RoundingMode.HALF_UP);
        Valutazione v = new Valutazione();
        v.setImmobile(immobile);
        v.setValore_stimato(estimated);
        v.setValore_min(min);
        v.setValore_max(max);
        v.setMetodo(MetodoValutazione.AUTOMATICO);
        v.setStato(StatoValutazione.COMPLETATA);
        v.setData_valutazione(LocalDateTime.now());
        return v;
    }

    @Override
    /**
     * Calcola la valutazione automatica partendo da un indirizzo e dai metadati.
     * Arricchisce il quartiere tramite Nominatim e normalizza alcune informazioni.
     * @return valutazione stimata non persistita
     */
    public Valutazione stimaAutomaticaDaIndirizzo(String provincia, String citta, String indirizzo, String civico, BigDecimal metriQuadri, Tipologia tipologia, CategoriaAbitazione categoria, StatoImmobile statoImmobile, String piano, Integer bagni, CaratteristicheImmobile caratteristiche) {
        Immobile temp = new Immobile();
        temp.setProvincia(provincia);
        temp.setCitta(citta);
        temp.setIndirizzo(indirizzo);
        temp.setCivico(civico);
        temp.setMetri_quadri(metriQuadri);
        temp.setTipologia(tipologia);
        temp.setStato_immobile(statoImmobile);
        temp.setPiano(piano);
        temp.setBagni(bagni);
        BigDecimal codiceTipologia = null;
        if (categoria != null) {
            switch (categoria) {
                case SIGNORILE -> codiceTipologia = BigDecimal.valueOf(19.00);
                case CIVILE -> codiceTipologia = BigDecimal.valueOf(20.00);
                case POPOLARE -> codiceTipologia = BigDecimal.valueOf(21.00);
                default -> {
                }
            }
        }
        String quartiere = serviceNominatim.resolveQuartiere(provincia, citta, indirizzo, civico);
        BigDecimal prezzo = null;
        String statoPreferito = statoImmobile == StatoImmobile.NUOVO ? "OTTIMO" : "NORMALE";
        if (quartiere != null && !quartiere.isBlank()) {
            var opt = (codiceTipologia != null)
                    ? serviceOmi.findPrezziByQuartiereTipologia(provincia, citta, quartiere, statoPreferito, codiceTipologia)
                    : serviceOmi.findPrezziByQuartiere(provincia, citta, quartiere, statoPreferito);
            if (opt.isPresent()) {
                var p = opt.get();
                if (null == statoImmobile) prezzo = p.min.add(p.max).divide(BigDecimal.valueOf(2));
                else prezzo = switch (statoImmobile) {
                    case NUOVO -> p.max;
                    case DA_RISTRUTTUARE -> p.min;
                    default -> p.min.add(p.max).divide(BigDecimal.valueOf(2));
                };
                Valutazione v = calcolaValutazione(temp, caratteristiche, prezzo);
                v.setNote("Zona OMI: " + p.zona);
                return v;
            }
        }
        var avgOpt = (codiceTipologia != null)
                ? serviceOmi.findPrezzoMedioByComuneTipologia(provincia, citta, statoPreferito, codiceTipologia)
                : serviceOmi.findPrezzoMedioByComune(provincia, citta, statoPreferito);
        if (avgOpt.isPresent()) {
            prezzo = avgOpt.get();
            return calcolaValutazione(temp, caratteristiche, prezzo);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo OMI non disponibile");
    }

    @Override
    /**
     * Crea e persiste una valutazione manuale partendo dalla stima automatica.
     * Se fornito, associa il valutatore.
     * @param idImmobile id immobile
     * @param idValutatore id valutatore (opzionale)
     * @param prezzoMqZona prezzo al mq della zona
     * @return valutazione salvata
     */
    public Valutazione creaValutazioneManuale(Integer idImmobile, Integer idValutatore, BigDecimal prezzoMqZona) {
        Valutazione v = stimaAutomatica(idImmobile, prezzoMqZona);
        v.setMetodo(MetodoValutazione.MANUALE);
        v.setStato(StatoValutazione.IN_LAVORAZIONE);
        if (idValutatore != null) {
            Utente valutatore = serviceUtente.getById(idValutatore);
            v.setValutatore(valutatore);
        }
        return repoValutazioni.save(v);
    }

    /**
     * Estrae il numero di piano da una stringa libera (es. "3", "P.T.").
     * Ritorna -1 in caso di valore non parsabile.
     */
    private int parseFloor(String s) {
        try {
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return -1;
            return Integer.parseInt(digits);
        } catch (Exception e) {
            return -1;
        }
    }

    public RepoImmobili getRepoImmobili() {
        return repoImmobili;
    }

    public void setRepoImmobili(RepoImmobili repoImmobili) {
        this.repoImmobili = repoImmobili;
    }
}

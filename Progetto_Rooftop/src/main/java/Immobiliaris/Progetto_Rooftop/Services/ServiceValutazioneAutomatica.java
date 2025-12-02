package Immobiliaris.Progetto_Rooftop.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import Immobiliaris.Progetto_Rooftop.Enum.StatoImmobile;    

import Immobiliaris.Progetto_Rooftop.DTO.RichiestaValutazioneDTO;
import Immobiliaris.Progetto_Rooftop.DTO.RispostaValutazioneDTO;

/**
 * Servizio per la valutazione automatica degli immobili.
 * NON salva nulla nel database - restituisce solo una stima.
 * 
 * Flusso:
 * 1. Riceve provincia e indirizzo
 * 2. Chiama Nominatim per trovare il quartiere e le coordinate
 * 3. Converte le coordinate da WGS84 a UTM 32N
 * 4. Cerca in omi_zone usando le coordinate (poligono) o il quartiere
 * 5. Trova la zona (es. B1) e i prezzi min/max
 * 6. Calcola la valutazione applicando moltiplicatori per caratteristiche
 */
@Service
public class ServiceValutazioneAutomatica {

    @Autowired
    private ServiceNominatim serviceNominatim;

    @Autowired
    private ServiceOmi serviceOmi;

    @Autowired
    private ServiceCoordinate serviceCoordinate;

    /**
     * Calcola la valutazione automatica dall'indirizzo e caratteristiche.
     * 
     * @param richiesta DTO con tutti i dati dell'immobile
     * @return DTO con valori stimati e info zona OMI
     */
    public RispostaValutazioneDTO calcolaValutazione(RichiestaValutazioneDTO richiesta) {
        // Validazione campi obbligatori
        if (richiesta.getProvincia() == null || richiesta.getProvincia().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provincia obbligatoria");
        }
        if (richiesta.getCap() == null || richiesta.getCap().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAP obbligatorio");
        }
        if (richiesta.getIndirizzo() == null || richiesta.getIndirizzo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indirizzo obbligatorio");
        }
        if (richiesta.getMetriQuadri() == null || richiesta.getMetriQuadri().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metri quadri obbligatori e > 0");
        }

        // 1. Chiama Nominatim per trovare quartiere, città e coordinate dall'indirizzo + CAP
        var nominatimResult = serviceNominatim.resolveIndirizzo(
            richiesta.getIndirizzo(),
            richiesta.getCivico(),
            richiesta.getCap(),
            richiesta.getProvincia()
        );

        String quartiere = null;
        String citta = null;
        Double latitudine = null;
        Double longitudine = null;
        
        if (nominatimResult != null) {
            quartiere = nominatimResult.quartiere;
            citta = nominatimResult.citta;
            latitudine = nominatimResult.latitudine;
            longitudine = nominatimResult.longitudine;
        }
        
        // Se Nominatim non ha trovato la città, errore
        if (citta == null || citta.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Impossibile determinare la città dal CAP " + richiesta.getCap() + ". Verifica l'indirizzo.");
        }

        // 2. Determina lo stato preferito per la ricerca OMI
        String statoPreferito = (richiesta.getStatoImmobile() == StatoImmobile.NUOVO) ? "OTTIMO" : "NORMALE";

        // 3. Determina codice tipologia OMI in base a tipologia e anno costruzione
        // - VILLA/VILLINO → cerca "ville e villini" (se disponibile) oppure codice 19 (signorile)
        // - APPARTAMENTO → se anno < 1970 → economico (21), altrimenti civile (20)
        BigDecimal codiceTipologia = null;
        String tipoDescrizione = "Abitazioni civili"; // default
        
        if (richiesta.getTipologia() != null) {
            switch (richiesta.getTipologia()) {
                case VILLA -> {
                    codiceTipologia = BigDecimal.valueOf(19.00); // Signorile (usato per ville)
                    tipoDescrizione = "Villa/Signorile";
                }
                case APPARTAMENTO, MONOLOCALE, ATTICO -> {
                    // Logica anno costruzione: pre-1970 = economico, post-1970 = civile
                    if (richiesta.getAnnoCost() != null && richiesta.getAnnoCost() < 1970) {
                        codiceTipologia = BigDecimal.valueOf(21.00); // Abitazioni economiche
                        tipoDescrizione = "Abitazioni di tipo economico";
                    } else {
                        codiceTipologia = BigDecimal.valueOf(20.00); // Abitazioni civili
                        tipoDescrizione = "Abitazioni civili";
                    }
                }
                default -> {
                    codiceTipologia = BigDecimal.valueOf(20.00); // Default civile
                    tipoDescrizione = "Abitazioni civili";
                }
            }
        } else {
            // Se tipologia non specificata, usa anno costruzione
            if (richiesta.getAnnoCost() != null && richiesta.getAnnoCost() < 1970) {
                codiceTipologia = BigDecimal.valueOf(21.00);
                tipoDescrizione = "Abitazioni di tipo economico";
            } else {
                codiceTipologia = BigDecimal.valueOf(20.00);
                tipoDescrizione = "Abitazioni civili";
            }
        }

        // 4. Cerca nella tabella omi_zone - PRIMA per coordinate (poligono), POI per quartiere testuale
        BigDecimal prezzoMq = null;
        String zonaOmi = null;
        String metodoRicerca = "quartiere";

        // Converti coordinate WGS84 (lat/lon) in UTM 32N (x/y in metri) per la ricerca nel poligono
        Double utmX = null;
        Double utmY = null;
        if (latitudine != null && longitudine != null) {
            double[] utm = serviceCoordinate.wgs84ToUtm(latitudine, longitudine);
            utmX = utm[0];
            utmY = utm[1];
        }

        // 4a. PRIORITÀ 1: Cerca tramite coordinate UTM nel poligono (più preciso)
        if (utmX != null && utmY != null) {
            var prezziCoordOpt = serviceOmi.findPrezziByCoordinate(
                richiesta.getProvincia().toUpperCase(),
                utmX,
                utmY,
                statoPreferito,
                codiceTipologia
            );
            
            if (prezziCoordOpt.isPresent()) {
                var prezzi = prezziCoordOpt.get();
                zonaOmi = prezzi.zona;
                quartiere = prezzi.quartiere; // Aggiorna con il quartiere trovato dal poligono
                metodoRicerca = "coordinate (poligono)";
                
                if (richiesta.getStatoImmobile() == null) {
                    prezzoMq = prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                } else {
                    prezzoMq = switch (richiesta.getStatoImmobile()) {
                        case NUOVO -> prezzi.max;
                        case StatoImmobile.DA_RISTRUTTURARE -> prezzi.min;
                        default -> prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                    };
                }
            }
        }

        // 4b. FALLBACK: Cerca tramite nome quartiere (se coordinate non hanno trovato nulla)
        if (prezzoMq == null && quartiere != null && !quartiere.isBlank()) {
            // Prima prova con tipologia specifica
            var prezziOpt = serviceOmi.findPrezziByQuartiereTipologia(
                richiesta.getProvincia().toUpperCase(),
                citta,
                quartiere,
                statoPreferito,
                codiceTipologia
            );

            if (prezziOpt.isPresent()) {
                var prezzi = prezziOpt.get();
                zonaOmi = prezzi.zona;
                
                // Calcola prezzo in base allo stato immobile
                if (richiesta.getStatoImmobile() == null) {
                    prezzoMq = prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                } else {
                    prezzoMq = switch (richiesta.getStatoImmobile()) {
                        case NUOVO -> prezzi.max;
                        case StatoImmobile.DA_RISTRUTTURARE -> prezzi.min;
                        default -> prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                    };
                }
            } else {
                // Fallback: cerca senza tipologia specifica
                var fallbackOpt = serviceOmi.findPrezziByQuartiere(
                    richiesta.getProvincia().toUpperCase(),
                    citta,
                    quartiere,
                    statoPreferito
                );
                if (fallbackOpt.isPresent()) {
                    var prezzi = fallbackOpt.get();
                    zonaOmi = prezzi.zona;
                    tipoDescrizione = "Media quartiere"; // non abbiamo la tipologia esatta
                    if (richiesta.getStatoImmobile() == null) {
                        prezzoMq = prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                    } else {
                        prezzoMq = switch (richiesta.getStatoImmobile()) {
                            case NUOVO -> prezzi.max;
                            case StatoImmobile.DA_RISTRUTTURARE -> prezzi.min;
                            default -> prezzi.min.add(prezzi.max).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                        };
                    }
                }
            }
        }

        // 5. Se non trovato per quartiere, cerca prezzo medio del comune con tipologia
        if (prezzoMq == null) {
            var avgOpt = serviceOmi.findPrezzoMedioByComuneTipologia(
                richiesta.getProvincia().toUpperCase(),
                citta,
                statoPreferito,
                codiceTipologia
            );

            if (avgOpt.isPresent()) {
                prezzoMq = avgOpt.get();
                zonaOmi = "MEDIA COMUNE";
            } else {
                // Ultimo fallback: media comune senza tipologia
                var fallbackAvg = serviceOmi.findPrezzoMedioByComune(
                    richiesta.getProvincia().toUpperCase(),
                    citta,
                    statoPreferito
                );
                if (fallbackAvg.isPresent()) {
                    prezzoMq = fallbackAvg.get();
                    zonaOmi = "MEDIA COMUNE";
                    tipoDescrizione = "Media generale";
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "Dati OMI non disponibili per questa zona. Provincia: " + richiesta.getProvincia() + ", Città: " + citta);
                }
            }
        }

        // 5. Calcola il valore base
        BigDecimal base = richiesta.getMetriQuadri().multiply(prezzoMq);
        BigDecimal multiplier = BigDecimal.ONE;

        // 6. Applica moltiplicatori per tipologia
        if (richiesta.getTipologia() != null) {
            multiplier = multiplier.multiply(switch (richiesta.getTipologia()) {
                case ATTICO -> BigDecimal.valueOf(1.03);
                case VILLA -> BigDecimal.valueOf(1.02);
                case MONOLOCALE -> BigDecimal.valueOf(0.98);
                default -> BigDecimal.ONE;
            });
        }

        // 7. Moltiplicatore bagni
        if (richiesta.getBagni() != null && richiesta.getBagni() > 1) {
            double extra = Math.min(richiesta.getBagni() - 1, 3);
            multiplier = multiplier.multiply(BigDecimal.valueOf(1 + (0.02 * extra)));
        }

        // 8. Moltiplicatori caratteristiche
        if (Boolean.TRUE.equals(richiesta.getAscensore())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.02));
        } else if (richiesta.getPiano() != null) {
            int pianoNum = parseFloor(richiesta.getPiano());
            if (pianoNum >= 3) {
                multiplier = multiplier.multiply(BigDecimal.valueOf(0.97));
            }
        }

        if (Boolean.TRUE.equals(richiesta.getParcheggio())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.01));
        }

        if (richiesta.getPostiAuto() != null && richiesta.getPostiAuto() > 0) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1 + (0.01 * richiesta.getPostiAuto())));
        }

        if (Boolean.TRUE.equals(richiesta.getGarage())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.03));
        }

        if (Boolean.TRUE.equals(richiesta.getCantina())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.015));
        }

        if (Boolean.TRUE.equals(richiesta.getArredato())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.01));
        }

        if (Boolean.TRUE.equals(richiesta.getAriaCondizionata())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.008));
        }

        if (Boolean.TRUE.equals(richiesta.getAllarme())) {
            multiplier = multiplier.multiply(BigDecimal.valueOf(1.006));
        }

        if (richiesta.getRiscaldamento() != null) {
            multiplier = multiplier.multiply(switch (richiesta.getRiscaldamento()) {
                case AUTONOMO -> BigDecimal.valueOf(1.02);
                case CENTRALIZZATO -> BigDecimal.ONE;
                case ASSENTE -> BigDecimal.valueOf(0.90);
            });
        }

        if (richiesta.getClasseEnergetica() != null) {
            multiplier = multiplier.multiply(switch (richiesta.getClasseEnergetica()) {
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
            });
        }

        if (richiesta.getOrientamento() != null) {
            multiplier = multiplier.multiply(switch (richiesta.getOrientamento()) {
                case SUD -> BigDecimal.valueOf(1.02);
                case SUD_EST, SUD_OVEST -> BigDecimal.valueOf(1.015);
                case EST, OVEST -> BigDecimal.valueOf(1.01);
                case NORD_EST, NORD_OVEST -> BigDecimal.valueOf(0.995);
                case NORD -> BigDecimal.valueOf(0.98);
            });
        }

        // 9. Calcola add-on per superfici esterne
        BigDecimal addons = BigDecimal.ZERO;

        if (richiesta.getTerrazzoMq() != null && richiesta.getTerrazzoMq().compareTo(BigDecimal.ZERO) > 0) {
            addons = addons.add(richiesta.getTerrazzoMq().multiply(prezzoMq).multiply(BigDecimal.valueOf(0.40)));
        }

        if (richiesta.getBalconeMq() != null && richiesta.getBalconeMq().compareTo(BigDecimal.ZERO) > 0) {
            addons = addons.add(richiesta.getBalconeMq().multiply(prezzoMq).multiply(BigDecimal.valueOf(0.20)));
        }

        if (richiesta.getGiardinoMq() != null && richiesta.getGiardinoMq().compareTo(BigDecimal.ZERO) > 0) {
            addons = addons.add(richiesta.getGiardinoMq().multiply(prezzoMq).multiply(BigDecimal.valueOf(0.20)));
        }

        // 10. Calcolo finale
        BigDecimal valoreStimato = base.multiply(multiplier).add(addons).setScale(2, RoundingMode.HALF_UP);
        BigDecimal margin = BigDecimal.valueOf(0.07); // ±7%
        BigDecimal valoreMin = valoreStimato.multiply(BigDecimal.ONE.subtract(margin)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal valoreMax = valoreStimato.multiply(BigDecimal.ONE.add(margin)).setScale(2, RoundingMode.HALF_UP);

        // 11. Crea risposta
        RispostaValutazioneDTO risposta = new RispostaValutazioneDTO(
            valoreStimato,
            valoreMin,
            valoreMax,
            prezzoMq,
            zonaOmi,
            quartiere
        );

        // Costruisce le note con tutte le info utili
        StringBuilder note = new StringBuilder();
        note.append("Metodo ricerca: ").append(metodoRicerca).append(". ");
        if (quartiere == null || quartiere.isBlank()) {
            note.append("Quartiere non rilevato, usato prezzo medio del comune. ");
        } else {
            note.append("Zona OMI: ").append(zonaOmi).append(" - Quartiere: ").append(quartiere).append(". ");
        }
        note.append("Tipologia OMI: ").append(tipoDescrizione);
        if (richiesta.getAnnoCost() != null) {
            note.append(" (anno costruzione: ").append(richiesta.getAnnoCost()).append(")");
        }
        if (latitudine != null && longitudine != null) {
            note.append(String.format(" [Coord: %.6f, %.6f]", latitudine, longitudine));
        }
        risposta.setNote(note.toString());

        return risposta;
    }

    /**
     * Estrae il numero di piano da una stringa (es. "3", "P.T.", "piano terra").
     */
    private int parseFloor(String s) {
        if (s == null || s.isBlank()) return 0;
        s = s.trim().toUpperCase();
        if (s.contains("TERRA") || s.equals("T") || s.equals("P.T.") || s.equals("PT")) return 0;
        if (s.contains("SEMINTERRATO") || s.equals("S") || s.equals("-1")) return -1;
        try {
            return Integer.parseInt(s.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

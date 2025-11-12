package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.ValutazioneZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;
import Immobiliaris.Progetto_Rooftop.Services.ServiceValutazioneZona;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller per gestione valutazioni al mq per zona.
 */
@RestController
@RequestMapping("/api/valutazioni-zona")
public class ControllerValutazioneZona {

    @Autowired
    private ServiceValutazioneZona serviceValutazioneZona;

    /** Restituisce tutte le valutazioni presenti. */
    @GetMapping
    public ResponseEntity<List<ValutazioneZona>> getAll() {
        return ResponseEntity.ok(serviceValutazioneZona.getAll());
    }

    /** Restituisce la valutazione per una zona (accetta enum name o displayName). */
    @GetMapping("/zona/{zona}")
    public ResponseEntity<?> getByZona(@PathVariable String zona) {
        try {
            ZonaProvinciaTorino zEnum = parseZona(zona);
            ValutazioneZona v = serviceValutazioneZona.getByZona(zEnum);
            return ResponseEntity.ok(v);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Zona non valida: " + zona));
        }
    }

    /** Restituisce tutte le valutazioni per una provincia. */
    @GetMapping("/provincia/{provincia}")
    public ResponseEntity<List<ValutazioneZona>> getByProvincia(@PathVariable String provincia) {
        return ResponseEntity.ok(serviceValutazioneZona.getAllByProvincia(provincia));
    }

    /** Restituisce la valutazione per provincia e zona. */
    @GetMapping("/provincia/{provincia}/zona/{zona}")
    public ResponseEntity<?> getByProvinciaAndZona(@PathVariable String provincia, @PathVariable String zona) {
        try {
            ZonaProvinciaTorino zEnum = parseZona(zona);
            ValutazioneZona v = serviceValutazioneZona.getByProvinciaAndZona(provincia, zEnum);
            return ResponseEntity.ok(v);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Zona non valida: " + zona));
        }
    }

    /** Elenco delle zone disponibili (displayName). */
    @GetMapping("/zone")
    public ResponseEntity<List<String>> getAvailableZone() {
        List<String> zone = Arrays.stream(ZonaProvinciaTorino.values())
                .map(ZonaProvinciaTorino::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(zone);
    }

    /** Crea o aggiorna (per zona) i valori di vendita/affitto. */
    @PostMapping
    public ResponseEntity<ValutazioneZona> upsert(@RequestBody ValutazioneZona payload) {
        ValutazioneZona saved = serviceValutazioneZona.upsert(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Aggiorna per id. */
    @PutMapping("/{id}")
    public ResponseEntity<ValutazioneZona> update(@PathVariable Long id, @RequestBody ValutazioneZona updated) {
        ValutazioneZona saved = serviceValutazioneZona.update(id, updated);
        return ResponseEntity.ok(saved);
    }

    /** Cancella per id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceValutazioneZona.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Calcola l'affitto totale per una zona con mq e opzionale provincia; se ammobiliato applica maggiorazione fissa. */
    @GetMapping("/affitto")
    public ResponseEntity<?> calcolaAffitto(@RequestParam("zona") String zona,
                                            @RequestParam("mq") BigDecimal mq,
                                            @RequestParam(value = "ammobiliato", defaultValue = "false") boolean ammobiliato,
                                            @RequestParam(value = "provincia", required = false) String provincia) {
        try {
            ZonaProvinciaTorino zEnum = parseZona(zona);
            BigDecimal totale = serviceValutazioneZona.calcolaAffitto(provincia, zEnum, mq, ammobiliato);
            return ResponseEntity.ok(Map.of("totaleAffitto", totale));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Zona non valida: " + zona));
        }
    }

    private ZonaProvinciaTorino parseZona(String raw) {
        // prova enum name
        try {
            return ZonaProvinciaTorino.valueOf(raw.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            // fallback displayName
            return Arrays.stream(ZonaProvinciaTorino.values())
                    .filter(z -> z.getDisplayName().equalsIgnoreCase(raw))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Zona non valida"));
        }
    }
}
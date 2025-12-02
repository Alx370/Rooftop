package Immobiliaris.Progetto_Rooftop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Immobiliaris.Progetto_Rooftop.DTO.RichiestaValutazioneDTO;
import Immobiliaris.Progetto_Rooftop.DTO.RispostaValutazioneDTO;
import Immobiliaris.Progetto_Rooftop.Services.ServiceValutazioneAutomatica;

/**
 * Controller per la valutazione automatica degli immobili.
 * Non salva nulla nel database, restituisce solo una stima.
 */
@RestController
@RequestMapping("/api/valutazione")
public class ControllerValutazione {

    @Autowired
    private ServiceValutazioneAutomatica serviceValutazione;

    /**
     * Endpoint per la valutazione automatica.
     * Riceve i dati dell'immobile, usa Nominatim per trovare il quartiere,
     * cerca nella tabella omi_zone e calcola la stima.
     * 
     * POST /api/valutazione/automatica
     * Body: RichiestaValutazioneDTO (JSON)
     * 
     * @param richiesta dati immobile e caratteristiche
     * @return RispostaValutazioneDTO con valori stimati e info zona OMI
     */
    @PostMapping("/automatica")
    public ResponseEntity<RispostaValutazioneDTO> valutazioneAutomatica(@RequestBody RichiestaValutazioneDTO richiesta) {
        RispostaValutazioneDTO risposta = serviceValutazione.calcolaValutazione(richiesta);
        return ResponseEntity.ok(risposta);
    }
}

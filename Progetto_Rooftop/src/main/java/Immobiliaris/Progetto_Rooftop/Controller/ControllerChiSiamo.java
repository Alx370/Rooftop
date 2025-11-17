package Immobiliaris.Progetto_Rooftop.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chi-siamo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControllerChiSiamo {

    // Endpoint per recuperare le informazioni generali su "Chi Siamo"
    @GetMapping
    public ResponseEntity<Map<String, Object>> getChiSiamo() {
        Map<String, Object> chiSiamo = new HashMap<>();
        chiSiamo.put("titolo", "La Nostra Storia");
        chiSiamo.put("descrizione", "Rooftop è una piattaforma innovativa dedicata alla gestione e valutazione immobiliare. " +
                "La nostra mission è semplificare il processo di compravendita immobiliare attraverso tecnologie avanzate " +
                "e un team di professionisti esperti.");
        
        return ResponseEntity.ok(chiSiamo);
    }

    // Endpoint per recuperare la missione e visione
    @GetMapping("/missione")
    public ResponseEntity<Map<String, Object>> getMissione() {
        Map<String, Object> missione = new HashMap<>();
        missione.put("titolo", "La Nostra Missione");
        missione.put("descrizione", "Fornire una soluzione completa e trasparente per la compravendita immobiliare, " +
                "garantendo valutazioni accurate e un'esperienza utente eccellente.");
        missione.put("visione", "Diventare la piattaforma di riferimento nel mercato immobiliare italiano, " +
                "riconosciuta per l'affidabilità, l'innovazione e la qualità dei servizi.");
        
        return ResponseEntity.ok(missione);
    }

    // Endpoint per recuperare i valori aziendali
    @GetMapping("/valori")
    public ResponseEntity<List<Map<String, Object>>> getValori() {
        List<Map<String, Object>> valori = List.of(
            Map.of(
                "id", 1,
                "titolo", "Trasparenza",
                "descrizione", "Operiamo con massima trasparenza nelle valutazioni e nelle comunicazioni con i nostri clienti."
            ),
            Map.of(
                "id", 2,
                "titolo", "Affidabilità",
                "descrizione", "Garantiamo servizi affidabili e tempestivi, supportati da professionisti qualificati."
            ),
            Map.of(
                "id", 3,
                "titolo", "Innovazione",
                "descrizione", "Investiamo continuamente in tecnologie innovative per migliorare la nostra piattaforma."
            ),
            Map.of(
                "id", 4,
                "titolo", "Professionalità",
                "descrizione", "Il nostro team è composto da esperti del settore immobiliare con anni di esperienza."
            )
        );
        
        return ResponseEntity.ok(valori);
    }

    // Endpoint per recuperare i dati del team
    @GetMapping("/team")
    public ResponseEntity<List<Map<String, Object>>> getTeam() {
        List<Map<String, Object>> team = List.of(
            Map.of(
                "id", 1,
                "nome", "Marco Rossi",
                "ruolo", "CEO & Founder",
                "biografia", "Con oltre 15 anni di esperienza nel settore immobiliare, Marco guida la visione strategica di Rooftop.",
                "foto", "/images/team/marco.jpg"
            ),
            Map.of(
                "id", 2,
                "nome", "Laura Bianchi",
                "ruolo", "Chief Technology Officer",
                "biografia", "Esperta in sviluppo software e architetture scalabili, Laura assicura l'eccellenza tecnica della piattaforma.",
                "foto", "/images/team/laura.jpg"
            ),
            Map.of(
                "id", 3,
                "nome", "Giovanni De Luca",
                "ruolo", "Head of Operations",
                "biografia", "Giovanni coordina le operazioni quotidiane e garantisce la qualità dei servizi forniti ai clienti.",
                "foto", "/images/team/giovanni.jpg"
            ),
            Map.of(
                "id", 4,
                "nome", "Francesca Moretti",
                "ruolo", "Head of Client Relations",
                "biografia", "Francesca gestisce le relazioni con i clienti, assicurando soddisfazione e supporto costante.",
                "foto", "/images/team/francesca.jpg"
            )
        );
        
        return ResponseEntity.ok(team);
    }

    // Endpoint di test per verificare la connessione
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Connessione al backend Chi Siamo stabilita con successo");
        
        return ResponseEntity.ok(response);
    }
}

package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import Immobiliaris.Progetto_Rooftop.Services.ServiceFaq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller exposing CRUD endpoints for managing FAQs.
 * Integrates with ServiceFaq to apply business rules and persistence operations.
 */
@RestController
@RequestMapping("/api/faq")
public class ControllerFaq {

    @Autowired
    private ServiceFaq serviceFaq;

    /**
     * GET /api/faq
     * Returns all FAQs ordered by 'ordine' ascending.
     */
    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaq() {
        List<Faq> faqs = serviceFaq.getAllOrdered();
        return ResponseEntity.ok(faqs);
    }

    /**
     * GET /api/faq/{id}
     * Returns a FAQ by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Faq> getFaqById(@PathVariable Integer id) {
        Faq faq = serviceFaq.getById(id);
        return ResponseEntity.ok(faq);
    }

    /**
     * GET /api/faq/categorie
     * Returns the available FAQ categories (enum names) for frontend consumption.
     */
    @GetMapping("/categorie")
    public ResponseEntity<List<String>> getCategorie() {
        List<String> categorie = Arrays.stream(CategoriaFaq.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorie);
    }

    /**
     * GET /api/faq/categoria/{categoria}
     * Returns FAQs filtered by category (case-insensitive), ordered by 'ordine'.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> getFaqByCategoria(@PathVariable String categoria) {
        try {
            CategoriaFaq cat = CategoriaFaq.valueOf(categoria.toUpperCase());
            List<Faq> faqs = serviceFaq.getByCategoria(cat);
            return ResponseEntity.ok(faqs);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid category: " + categoria));
        }
    }

    /**
     * POST /api/faq
     * Creates a new FAQ.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')") // only admin or agent can create FAQs
    public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
        Faq created = serviceFaq.create(faq);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/faq/{id}
     * Updates an existing FAQ.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')") // only admin or agent can update FAQs
    public ResponseEntity<Faq> updateFaq(@PathVariable Integer id, @RequestBody Faq updated) {
        Faq saved = serviceFaq.update(id, updated);
        return ResponseEntity.ok(saved);
    }

    /**
     * DELETE /api/faq/{id}
     * Deletes a FAQ by ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')") // only admin can delete FAQs
    public ResponseEntity<Void> deleteFaq(@PathVariable Integer id) {
        serviceFaq.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/faq/from-request/{richiestaId}
     * Creates a new FAQ from a contact request.
     * Admin sends the request ID and provides the answer (risposta) and category.
     * The question is taken from the contact request's message.
     * 
     * Request body example:
     * {
     *   "risposta": "La risposta è...",
     *   "categoria": "GENERALE"
     * }
     */
    @PostMapping("/from-request/{richiestaId}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<?> createFaqFromRequest(
            @PathVariable Integer richiestaId,
            @RequestBody Map<String, String> body) {
        try {
            String rispostaStr = body.get("risposta");
            String categoriaStr = body.get("categoria");

            if (rispostaStr == null || rispostaStr.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Risposta è richiesta"));
            }

            if (categoriaStr == null || categoriaStr.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Categoria è richiesta"));
            }

            // Parse the category enum
            CategoriaFaq categoria;
            try {
                categoria = CategoriaFaq.valueOf(categoriaStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Categoria non valida: " + categoriaStr));
            }

            // Create the FAQ from the request
            Faq faq = serviceFaq.createFromRequest(richiestaId, rispostaStr, categoria);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "FAQ creata con successo dalla richiesta",
                            "data", faq
                    ));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore interno: " + e.getMessage()));
        }
    }
}
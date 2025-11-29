package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Enum.CategoriaFaq;
import Immobiliaris.Progetto_Rooftop.Model.Faq;
import Immobiliaris.Progetto_Rooftop.Services.ServiceFaq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


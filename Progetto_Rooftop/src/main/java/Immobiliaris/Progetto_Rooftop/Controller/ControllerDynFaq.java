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


@RestController
@RequestMapping("/api/faq")
public class ControllerDynFaq {

    @Autowired
    private ServiceFaq serviceFaq;


    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaq() {
        List<Faq> faqs = serviceFaq.getAllOrdered();
        return ResponseEntity.ok(faqs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Faq> getFaqById(@PathVariable Integer id) {
        Faq faq = serviceFaq.getById(id);
        return ResponseEntity.ok(faq);
    }


    @GetMapping("/categorie")
    public ResponseEntity<List<String>> getCategorie() {
        List<String> categorie = Arrays.stream(CategoriaFaq.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorie);
    }


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


    @PostMapping
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')")
    public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
        Faq created = serviceFaq.create(faq);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AMMINISTRATORE', 'AGENTE')") 
    public ResponseEntity<Faq> updateFaq(@PathVariable Integer id, @RequestBody Faq updated) {
        Faq saved = serviceFaq.update(id, updated);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> deleteFaq(@PathVariable Integer id) {
        serviceFaq.delete(id);
        return ResponseEntity.noContent().build();
    }
}
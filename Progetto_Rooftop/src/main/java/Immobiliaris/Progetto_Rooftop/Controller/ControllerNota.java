package Immobiliaris.Progetto_Rooftop.Controller;

import Immobiliaris.Progetto_Rooftop.Model.Nota;
import Immobiliaris.Progetto_Rooftop.Model.TipoNota;
import Immobiliaris.Progetto_Rooftop.Model.VisibilitaNota;
import Immobiliaris.Progetto_Rooftop.Repos.RepoNota;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class ControllerNota {

    private final RepoNota repoNota;

    public ControllerNota(RepoNota repoNota) {
        this.repoNota = repoNota;
    }

    @GetMapping
    public List<Nota> getAll() {
        return repoNota.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> getOne(@PathVariable Integer id) {
        return repoNota.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/immobili/{idImmobile}")
    public List<Nota> getByImmobile(
            @PathVariable Integer idImmobile,
            @RequestParam(name = "visibilita", required = false) VisibilitaNota visibilita
    ) {
        if (visibilita != null) {
            return repoNota.findAllByIdImmobileAndVisibilitaOrderByCreatedAtDesc(idImmobile, visibilita);
        }
        return repoNota.findAllByIdImmobileOrderByCreatedAtDesc(idImmobile);
    }

    @GetMapping("/agenti/{idAgente}")
    public List<Nota> getByAgente(@PathVariable Integer idAgente) {
        return repoNota.findAllByAgente_IdUtenteOrderByCreatedAtDesc(idAgente);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Nota nota) {
        // Validazioni minime
        if (nota.getAgente() == null || nota.getAgente().getId_utente() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_agente mancante");
        }
        if (nota.getContenuto() == null || nota.getContenuto().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("contenuto richiesto");
        }
        if (nota.getTipo() == null) {
            nota.setTipo(TipoNota.INTERNO);
        }
        if (nota.getVisibilita() == null) {
            nota.setVisibilita(VisibilitaNota.TEAM);
        }
        Nota saved = repoNota.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Nota input) {
        return repoNota.findById(id).map(existing -> {
            if (input.getContenuto() != null && !input.getContenuto().trim().isEmpty()) {
                existing.setContenuto(input.getContenuto().trim());
            }
            if (input.getVisibilita() != null) {
                existing.setVisibilita(input.getVisibilita());
            }
            if (input.getTipo() != null) {
                existing.setTipo(input.getTipo());
            }
            if (input.getId_immobile() != null) {
                existing.setId_immobile(input.getId_immobile());
            }
            if (input.getAgente() != null && input.getAgente().getId_utente() != null) {
                existing.setAgente(input.getAgente());
            }
            Nota updated = repoNota.save(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return repoNota.findById(id).map(existing -> {
            repoNota.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
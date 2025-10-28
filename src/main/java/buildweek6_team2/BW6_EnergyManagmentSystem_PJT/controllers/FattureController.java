package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fatture;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.FatturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/fatture")
public class FattureController {
    @Autowired
    private FatturaService fattureService;

    @Autowired
    public FattureController(FatturaService fattureService) {
        this.fattureService = fattureService;
    }

    @GetMapping
    public ResponseEntity<List<Fatture>> getAll() {
        return ResponseEntity.ok(fattureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fatture> getById(@PathVariable Long id) {
        Optional<Fatture> opt = fattureService.findById(id);
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fatture> create(@Valid @RequestBody Fatture fattura) {
        Fatture saved = fattureService.save(fattura);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fatture> update(@PathVariable UUID id, @Valid @RequestBody Fatture fattura) {
        Optional<Fatture> updated = fattureService.update(id, fattura);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = fattureService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}


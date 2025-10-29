import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
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
public class FatturaController {
    @Autowired
    private FatturaService fattureService;


    @GetMapping
    public ResponseEntity<List<Fattura>> getAll() {
        return ResponseEntity.ok(fattureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fattura> getById(@PathVariable UUID id) {
        Optional<Fattura> opt = fattureService.findById(id);
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fattura> create(@Valid @RequestBody FatturaDTO newFattura) {


        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fattura> update(@PathVariable UUID id, @Valid @RequestBody Fattura fattura) {
        Optional<Fattura> updated = fattureService.update(id, fattura);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = fattureService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}


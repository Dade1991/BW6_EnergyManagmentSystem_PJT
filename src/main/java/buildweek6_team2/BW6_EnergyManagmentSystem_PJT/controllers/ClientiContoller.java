package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Clienti;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ClientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clienti")
public class ClientiContoller {

    private final ClientiService clientiService;

    @Autowired
    public ClientiContoller(ClientiService clientiService) {
        this.clientiService = clientiService;
    }

    @GetMapping
    public ResponseEntity<List<Clienti>> getAll() {
        return ResponseEntity.ok(clientiService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clienti> getById(@PathVariable Long id) {
        Optional<Clienti> opt = Optional.ofNullable(clientiService.findById(id));
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Clienti> create(@Valid @RequestBody Clienti cliente) {
        Clienti saved = clientiService.save(cliente);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clienti> update(@PathVariable UUID id, @Valid @RequestBody Clienti cliente) {
        Optional<Clienti> updated = Optional.ofNullable(clientiService.update(id, cliente));
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = clientiService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

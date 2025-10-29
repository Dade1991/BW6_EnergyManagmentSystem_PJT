package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;


import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ClientiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClientiService clientiService;

    // GET http://localhost:3001/clienti

    @GetMapping
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "clienteId") String sortBy) {
        return clientiService.findAllClienti(page, size, sortBy);
    }

    // GET http://localhost:3001/clienti/{clienteId}

    @GetMapping("/{clienteId}")
    public Cliente getClienteById(@PathVariable Long clienteId) {
        return clientiService.findClientiById(clienteId);
    }

    // PUT http://localhost:3001/clienti/{clienteId} (+ payload)

    @PutMapping("/{clienteId}")
    public Cliente

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente) {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long clienteId) {
    }
}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;


import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.ValidationException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.ClienteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ClientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClientiService clientiService;

    // GET http://localhost:3001/clienti

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Cliente> getAllClienti(
            @RequestParam(required = false) Double fatturatoAnnuale,
            @RequestParam(required = false) LocalDate dataInserimento,
            @RequestParam(required = false) LocalDate dataUltimoContatto,
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clienteId") String sortBy) {
        return clientiService.trovaClienti(fatturatoAnnuale, dataInserimento, dataUltimoContatto, nome, page, size, sortBy);
    }

    // GET http://localhost:3001/clienti/{clienteId}

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Cliente getClienteById(@PathVariable Long clienteId) {
        return clientiService.trovaClientePerId(clienteId);
    }

    // PUT http://localhost:3001/clienti/{clienteId} (+ payload)

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente getClienteByIdAndUpdate(@PathVariable Long clienteId, @RequestBody ClienteDTO bodyCliente){
        return this.clientiService.findClientiByIdAndUpdate(clienteId, bodyCliente);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente createCliente(@RequestBody @Validated ClienteDTO bodyCliente, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.clientiService.salvaCliente(bodyCliente);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{clienteId}")
    public void deleteCliente(@PathVariable Long clienteId) {
        this.clientiService.eliminaClientePerId(clienteId);
    }
}

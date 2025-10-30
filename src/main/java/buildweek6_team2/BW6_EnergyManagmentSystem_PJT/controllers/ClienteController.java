package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;


import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.ValidationException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.ClienteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.ClientiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public Cliente getClienteByIdAndUpdate(@PathVariable Long clienteId, ClienteDTO bodyCliente){
        return this.clientiService.findClientiByIdAndUpdate(clienteId, bodyCliente);
    }

    @PostMapping
    public Cliente createCliente(@RequestBody @Validated ClienteDTO bodyCliente, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.clientiService.saveClienti(bodyCliente);
    }

    @DeleteMapping("/{clienteId}")
    public void deleteCliente(@PathVariable Long clienteId) {
        this.clientiService.deleteClienti(clienteId);
    }
}

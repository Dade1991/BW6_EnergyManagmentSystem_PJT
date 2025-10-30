package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.ValidationException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fattureService;

    // GET http://localhost:3001/fatture

    @GetMapping
    public Page<Fattura> getAllFatture(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "clienteId") String sortBy) {
        return fattureService.findAllFatture(page, size, sortBy);
    }

    // GET http://localhost:3001/fatture/{idFattura}

    @GetMapping("/{idFattura}")
    public Fattura getFatturaById(@PathVariable Long idFattura) {
        return fattureService.findFatturaById(idFattura);
    }

    // PUT http://localhost:3001/fatture/{clienteId} (+ payload)

    @PutMapping("/{idFattura}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura findFatturaByIdAndUpdate(@PathVariable Long idFattura,
                                            @RequestBody FatturaDTO payload) {
        return fattureService.findFatturaByIdAndUpdate(idFattura, payload);
    }

    // POST http://localhost:3001/fatture (+ body)

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public FatturaDTO saveFattura(@RequestBody @Validated FatturaDTO payload, BindingResult validation)
            throws Exception {
        if (validation.hasErrors()) {
            throw new ValidationException(validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        Fattura newFattura = fattureService.saveFattura(payload);
        return new FatturaDTO(
                newFattura.getData(),
                newFattura.getImporto(),
                newFattura.getNumero(),
                newFattura.getCliente().getClienteId()
        );
    }

    // DELETE http://localhost:3001/fatture/{id}

    @DeleteMapping("/{idFattura}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDeleteFattura(@PathVariable Long idFattura) {
        fattureService.findByIdAndDeleteFattura(idFattura);
    }
}
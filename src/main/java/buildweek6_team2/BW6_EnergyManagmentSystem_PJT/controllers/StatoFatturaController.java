package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.ValidationException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.StatoFatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stati-fattura")
public class StatoFatturaController {
    @Autowired
    private StatoFatturaService statoFatturaService;

    // GET http://localhost:3001/stati-fattura

    @GetMapping
    public Page<StatoFattura> getAllStatiFattura(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "idStatoFattura") String sortBy) {
        return statoFatturaService.findAllStatiFattura(page, size, sortBy);
    }

    // GET http://localhost:3001/stati-fattura/{idStatoFattura}

    @GetMapping("/{idStatoFattura}")
    public StatoFattura getStatoFatturaById(@PathVariable Long idStatoFattura) {
        return statoFatturaService.findStatoFatturaById(idStatoFattura);
    }

    // GET http://localhost:3001/stati-fattura/by-stato/{stato}

    @GetMapping("/by-stato/{stato}")
    public StatoFattura getStatoFatturaByStato(@PathVariable String stato) {
        return statoFatturaService.findStatoFatturaByStato(stato);
    }

    // POST http://localhost:3001/stati-fattura (+ payload)

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatoFatturaDTO saveStatoFattura(@RequestBody @Validated StatoFatturaDTO payload, BindingResult validation)
            throws Exception {
        if (validation.hasErrors()) {
            throw new ValidationException(validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        StatoFattura newStato = statoFatturaService.saveStatoFattura(payload);
        return new StatoFatturaDTO(newStato.getStato());
    }

    // PUT http://localhost:3001/stati-fattura/{idStatoFattura} (+ payload)

    @PutMapping("/{idStatoFattura}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatoFattura updateStatoFattura(@PathVariable Long idStatoFatture,
                                           @RequestBody StatoFatturaDTO payload) {
        return statoFatturaService.findStatoFatturaByIdAndUpdate(idStatoFatture, payload);
    }
}
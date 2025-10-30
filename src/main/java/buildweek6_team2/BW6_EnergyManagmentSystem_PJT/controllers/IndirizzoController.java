package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Indirizzo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.IndirizzoDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo createIndirizzo(@RequestBody @Validated IndirizzoDTO newIndirizzoPayload) {

        Indirizzo nuovoIndirizzo = new Indirizzo(
                newIndirizzoPayload.via(),
                newIndirizzoPayload.civico(),
                newIndirizzoPayload.localita(),
                newIndirizzoPayload.cap()
        );
        return indirizzoService.saveIndirizzo(nuovoIndirizzo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Indirizzo findIndirizzoById(@PathVariable Long id) {
        return indirizzoService.findByIdIndirizzo(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable Long id) {
        indirizzoService.deleteIndirizzo(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo findAndUpdate(@PathVariable Long id, @RequestBody @Validated IndirizzoDTO modifiedIndirizzoPayload) {
        Indirizzo indirizzo = indirizzoService.findByIdIndirizzo(id);

        
        indirizzo.setVia(modifiedIndirizzoPayload.via());
        indirizzo.setCivico(modifiedIndirizzoPayload.civico());
        indirizzo.setLocalita(modifiedIndirizzoPayload.localita());
        indirizzo.setCap(modifiedIndirizzoPayload.cap());

        return indirizzoService.saveIndirizzo(indirizzo);
    }
}

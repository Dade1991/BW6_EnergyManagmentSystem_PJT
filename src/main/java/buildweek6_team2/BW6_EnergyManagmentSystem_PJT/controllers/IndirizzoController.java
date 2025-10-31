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

        return this.indirizzoService.saveIndirizzo(newIndirizzoPayload);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Indirizzo findIndirizzoById(@PathVariable Long id) {
        return this.indirizzoService.findByIdIndirizzo(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable Long id) {
        this.indirizzoService.deleteIndirizzo(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo findAndUpdate(@PathVariable Long id, @RequestBody @Validated IndirizzoDTO modifiedIndirizzoPayload) {

        return this.indirizzoService.saveIndirizzo(modifiedIndirizzoPayload);
    }
}

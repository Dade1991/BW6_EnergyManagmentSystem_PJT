package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.RuoloDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    @Autowired
    private RuoloService ruoloService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Ruolo saveRuolo(@RequestBody @Validated RuoloDTO payload) {

        return this.ruoloService.saveRuolo(payload);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Ruolo findRuoloById(@PathVariable Long id) {
        return ruoloService.findByIdRuolo(id);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Ruolo findAndUpdate(@PathVariable Long id, @RequestBody @Validated RuoloDTO modifiedRuoloPayload) {

        return ruoloService.updateRuolo(id, modifiedRuoloPayload);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable Long id) {
        ruoloService.deleteRuolo(id);
    }
}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.controllers;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.ValidationException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.LoginDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.LoginResponseDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.UtenteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.AuthService;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UtentiService utentiService;

    // 1. POST per login
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkAndCreateToken(body));
    }


    // 2. POST per registration
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated UtenteDTO bodyUtente, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.utentiService.saveUtenti(bodyUtente);
    }

}

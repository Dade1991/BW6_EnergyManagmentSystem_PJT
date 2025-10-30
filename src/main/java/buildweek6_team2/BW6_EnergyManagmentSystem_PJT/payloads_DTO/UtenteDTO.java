package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UtenteDTO(

        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve avere un minimo di due caratteri e un massimo di 20")
        String username,
        @NotBlank(message = "L'e-mail è obbligatoria")
        @Email(message = "L'indirizzo e-mail non è nel formato giusto")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        String password,
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve avere un minimo di due caratteri e un massimo di 20")
        String nome,
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il cognome deve avere un minimo di due caratteri e un massimo di 20")
        String cognome
) {
}
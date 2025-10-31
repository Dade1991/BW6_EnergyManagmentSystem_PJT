package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IndirizzoDTO(
        @NotBlank(message = "La via è obbligatoria")
        @Size(min = 3, max = 30, message = "La via deve essere tra 3 e 30 caratteri")
        String via,

        @NotBlank(message = "Il civico è obbligatorio")
        @Size(min = 1, max = 10, message = "Il civico deve essere tra 1 e 10 caratteri")
        String civico,

        @NotBlank(message = "La località è obbligatoria")
        @Size(min = 2, max = 50, message = "La località deve essere tra 2 e 50 caratteri")
        String localita,

        @NotBlank(message = "Il cap è obbligatorio")
        @Size(min = 5, max = 5, message = "Il cap deve essere composto da 5 numeri")

        String cap,

        @NotNull(message = "L'ID del comune non può essere nullo")
        Long idComune
) {
}

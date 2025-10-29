package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record FatturaDTO(

        @NotBlank(message = "Importo obbligatorio")
        @PositiveOrZero(message = "Il fatturato annuale non può essere negativo")
        Double importo,
        @NotBlank(message = "Il numero della fattura è obbligatoria")
        @Size(min = 1, max = 12, message = "Il numero della fattura deve avere un minimo di 1 caratteri e un massimo di 12")
        String numero,
        @NotNull(message = "L'ID del cliente è obbligatorio per la fattura")
        UUID idCliente
) {
}

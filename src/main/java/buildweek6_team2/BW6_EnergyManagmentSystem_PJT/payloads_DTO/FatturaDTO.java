package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record FatturaDTO(
        @NotNull
        @FutureOrPresent
        LocalDate data,
        @NotNull(message = "Importo obbligatorio")
        @PositiveOrZero(message = "Il fatturato annuale non può essere negativo")
        Double importo,
        @NotBlank(message = "Il numero della fattura è obbligatoria")
        @Size(min = 1, max = 12, message = "Il numero della fattura deve avere un minimo di 1 caratteri e un massimo di 12")
        String numero,
        @NotNull(message = "L'ID del cliente è obbligatorio per la fattura")
        Long idCliente,
        @NotNull(message = "L'ID dello stato della fattura")
        Long idStatoFattura
) {
}

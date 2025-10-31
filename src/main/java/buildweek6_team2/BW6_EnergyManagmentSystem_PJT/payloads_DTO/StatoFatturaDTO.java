package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import jakarta.validation.constraints.NotBlank;

public record StatoFatturaDTO(
        @NotBlank
        String stato
) {
}

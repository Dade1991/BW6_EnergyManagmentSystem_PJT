package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWithListDTO(String message,
                                LocalDateTime timestamp,
                                List<String> errorsList) {
}

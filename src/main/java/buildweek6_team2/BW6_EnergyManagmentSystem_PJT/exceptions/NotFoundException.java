package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Il record con id " + String.valueOf(id) + " non è stato trovato!");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}

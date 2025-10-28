package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);
}

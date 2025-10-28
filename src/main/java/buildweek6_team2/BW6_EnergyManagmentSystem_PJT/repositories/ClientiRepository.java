package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Clienti;

@Repository
public interface ClientiRepository extends JpaRepository<Clienti, Long> {
    Optional<Clienti> findByEmail(String email);

    boolean existsById(UUID id);
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    
}

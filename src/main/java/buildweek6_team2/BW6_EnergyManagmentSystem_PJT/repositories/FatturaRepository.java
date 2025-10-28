package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fatture;

@Repository
public interface FatturaRepository extends JpaRepository<Fatture, Long> {
    List<Fatture> findByClienteId(Long clienteId);
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long> {
}

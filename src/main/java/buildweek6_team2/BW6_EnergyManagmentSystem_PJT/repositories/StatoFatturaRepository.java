package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long> {
    Optional<StatoFattura> findByStato(String stato);

    Optional<StatoFattura> findByIdStatoFattura(Long idStatoFattura);
}

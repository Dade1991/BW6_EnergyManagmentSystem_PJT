package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Provincia;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Provincia save(Provincia provincia) {
        return provinciaRepository.save(provincia);
    }

    public Optional<Provincia> findById(Long id) {
        return provinciaRepository.findById(id);
    }

    public Optional<Provincia> findBySigla(String sigla) {
        return provinciaRepository.findBySigla(sigla);
    }

    public long count() {
        return provinciaRepository.count();
    }
}

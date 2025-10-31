package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Comune;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    public Comune save(Comune comune) {
        return comuneRepository.save(comune);
    }

    public Comune findComuneById(Long idComune) {
        return this.comuneRepository.findById(idComune).orElseThrow(() ->
             new IdNotFoundException("Il comune con ID " + idComune + " non è stato trovato"));

    }

    public long count() {
        return comuneRepository.count();
    }
}

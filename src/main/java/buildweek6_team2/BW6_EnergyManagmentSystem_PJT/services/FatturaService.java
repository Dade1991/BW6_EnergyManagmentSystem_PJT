package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fatture;

@Service
public class FatturaService {

    private final FatturaRepository fatturaRepository;

    public FatturaService(FatturaRepository fatturaRepository) {
        this.fatturaRepository = fatturaRepository;
    }

    public List<Fatture> findAll() {
        return fatturaRepository.findAll();
    }

    public Fatture findById(Long id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fattura non trovata: " + id));
    }

    @Transactional
    public Fatture create(Fatture fattura) {
        return fatturaRepository.save(fattura);
    }

    @Transactional
    public Fatture update(Long id, Fatture fattura) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fattura.setId(id);
        return fatturaRepository.save(fattura);
    }

    @Transactional
    public void delete(Long id) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fatturaRepository.deleteById(id);
    }
}

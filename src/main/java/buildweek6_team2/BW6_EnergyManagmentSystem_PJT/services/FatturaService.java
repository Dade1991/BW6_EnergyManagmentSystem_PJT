package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FatturaService {

    private final FatturaRepository fatturaRepository;

    public FatturaService(FatturaRepository fatturaRepository) {
        this.fatturaRepository = fatturaRepository;
    }

    public List<Fattura> findAll() {
        return fatturaRepository.findAll();
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fattura non trovata: " + id));
    }

    @Transactional
    public Fattura create(Fattura fattura) {
        return fatturaRepository.save(fattura);
    }

    @Transactional
    public Fattura update(UUID id, Fattura fattura) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fattura.setId(id);
        return fatturaRepository.save(fattura);
    }

    @Transactional
    public void delete(UUID id) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fatturaRepository.deleteById(id);
    }
}

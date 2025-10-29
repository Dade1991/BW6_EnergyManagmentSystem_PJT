package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClientiService clientiService;


    public List<Fattura> findAll() {
        return fatturaRepository.findAll();
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata: " + id));
    }


    public Fattura create(FatturaDTO payload) {

        this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {
            throw new BadRequestException("La fattura " + fattura.getNumero() + " è già in uso!");
        });

        Fattura newFattura = new Fattura();

        newFattura.setData(LocalDate.now());
        newFattura.setImporto(payload.importo());
        newFattura.setNumero(payload.numero());
        newFattura.setCliente(this.clientiService.findById(payload.idCliente()));

        return newFattura;
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

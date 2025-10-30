package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClientiService clientiService;

    // FIND ALL

    public Page<Fattura> findAllFatture(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.fatturaRepository.findAll(pageable);
    }


    // SAVE

    public Fattura saveFattura(FatturaDTO payload) {
        this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {
            throw new BadRequestException("The e-mail " + fattura.getNumero() + " is already in use.");
        });

        Fattura newFattura = new Fattura(payload.importo(),
                payload.numero()
        );


        log.info("The user with ID: " + " has been duly saved.");

        return savedUtente;
    }


    public Fattura findById(Long id) {
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
    public Fattura update(Long id, Fattura fattura) {
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

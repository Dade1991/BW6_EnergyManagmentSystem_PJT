package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
            throw new BadRequestException("The invoice n°: " + fattura.getNumero() + " is already in use.");
        });

        Cliente found = this.clientiService.findClientiById(payload.idCliente());

        Fattura newFattura = new Fattura(
                payload.data(),
                payload.importo(),
                payload.numero(),
                found
        );

        newFattura.setData(payload.data());
        newFattura.setImporto(payload.importo());
        newFattura.setNumero(payload.numero());

        Fattura savedFattura = this.fatturaRepository.save(newFattura);

        return savedFattura;
    }

    // FIND BY ID & UPDATE

    public Fattura findFatturaByIdAndUpdate(Long idFattura,
                                            FatturaDTO payload) {
        Fattura found = this.findFatturaById(idFattura);

        if (!found.getNumero().equals(payload.numero())) {
            this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {

            });
        }

        found.setData(payload.data());
        found.setImporto(payload.importo());
        found.setNumero(payload.numero());

        Fattura modifyFattura = this.fatturaRepository.save(found);

        return modifyFattura;
    }

    // FIND BY ID & DELETE

    public void findByIdAndDeleteFattura(Long idFattura) {
        Fattura found = this.findFatturaById(idFattura);
        this.fatturaRepository.delete(found);
    }

    // FIND BY ID

    public Fattura findFatturaById(Long idFattura) {
        return this.fatturaRepository.findById(idFattura).orElseThrow(() -> new IdNotFoundException("The invoice n°: " + idFattura + " has not been found."));
    }
}
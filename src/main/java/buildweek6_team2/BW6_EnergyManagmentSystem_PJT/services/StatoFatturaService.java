package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.StatoFatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatoFatturaService {
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    // SAVE

    public StatoFattura saveStatoFattura(StatoFatturaDTO payload) {
        this.statoFatturaRepository.findByStato(payload.stato()).ifPresent(statoFattura -> {
            throw new BadRequestException("ID status " + statoFattura.getStato() + " already applied & in use. Change it with a new one.");
        });

        StatoFattura newStatoFattura = new StatoFattura(
                payload.stato()
        );

        newStatoFattura.setStato(payload.stato());

        StatoFattura savedStatoFattura = this.statoFatturaRepository.save(newStatoFattura);

        return savedStatoFattura;
    }

    // FIND BY ID & UPDATE

    public StatoFattura findStatoFatturaByIdAndUpdate(Long idStatoFattura,
                                                      StatoFatturaDTO payload) {
        StatoFattura found = this.findStatoFatturaById(idStatoFattura);

        if (!found.getStato().equals(payload.stato())) {
            this.statoFatturaRepository.findByStato(payload.stato()).ifPresent(statoFattura -> {
                throw new BadRequestException("ID status '" + payload.stato() + "' is already in use.");
            });
        }

        found.setStato(payload.stato());

        StatoFattura modifyStatoFattura = this.statoFatturaRepository.save(found);

        return modifyStatoFattura;
    }

    // FIND BY ID

    public StatoFattura findStatoFatturaById(Long idStatoFattura) {
        return this.statoFatturaRepository.findByIdStatoFattura(idStatoFattura)
                .orElseThrow(() -> new IdNotFoundException("ID status " + idStatoFattura + " has not been found."));
    }

    // FIND BY STATUS

    public StatoFattura findStatoFatturaByStato(String stato) {
        return this.statoFatturaRepository.findByStato(stato)
                .orElseThrow(() -> new NotFoundException("Status " + stato + " has not been found."));
    }

    // FIND ALL

    public Page<StatoFattura> findAllStatiFattura(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return this.statoFatturaRepository.findAll(pageable);
    }
}
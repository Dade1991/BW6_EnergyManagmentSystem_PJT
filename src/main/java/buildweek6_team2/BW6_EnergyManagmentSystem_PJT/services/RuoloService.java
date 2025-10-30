package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.RuoloDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;

    public Ruolo saveRuolo(RuoloDTO payload) {
        this.ruoloRepository.findBytipoRuolo(payload.tipoRuolo()).ifPresent(ruolo -> {
            throw new BadRequestException("il ruolo " + ruolo.getTipoRuolo() + " è gia in uso.");
        });
        Ruolo newRuolo = new Ruolo();

        newRuolo.setTipoRuolo(payload.tipoRuolo());

        return this.ruoloRepository.save(newRuolo);
    }

    public Ruolo findByIdRuolo(Long id) {
        return ruoloRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Ruolo con id " + id + " non trovato!"));
    }

    public Ruolo updateRuolo(Long id, RuoloDTO payload) {
        Ruolo found = this.findByIdRuolo(id);


        found.setTipoRuolo(payload.tipoRuolo());

        return this.ruoloRepository.save(found);
    }

    public void deleteRuolo(Long id) {
        Ruolo ruolo = this.findByIdRuolo(id);
        this.ruoloRepository.delete(ruolo);
    }


}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Indirizzo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    public Indirizzo saveIndirizzo(Indirizzo indirizzo) {
        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo findByIdIndirizzo(Long id) {
        return indirizzoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Indirizzo con id " + id + " non trovato!"));
    }

    public void deleteIndirizzo(Long id) {
        Indirizzo indirizzo = this.findByIdIndirizzo(id);
        indirizzoRepository.delete(indirizzo);
    }

    public Indirizzo updateIndirizzo(Long id, Indirizzo payload) {
        Indirizzo found = this.findByIdIndirizzo(id);
        
        found.setVia(payload.getVia());
        found.setCivico(payload.getCivico());
        found.setLocalita(payload.getLocalita());
        found.setCap(payload.getCap());

        return indirizzoRepository.save(found);
    }


}

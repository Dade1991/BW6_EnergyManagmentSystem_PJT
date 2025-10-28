package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ClientiRepository;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Clienti;

@Service
public class ClientiService {

    private final ClientiRepository clientiRepository;

    public ClientiService(ClientiRepository clientiRepository) {
        this.clientiRepository = clientiRepository;
    }

    public List<Clienti> findAll() {
        return clientiRepository.findAll();
    }

    public Clienti findById(Long id) {
        return clientiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato: " + id));
    }

    @Transactional
    public Clienti create(Clienti cliente) {
        return clientiRepository.save(cliente);
    }

    @Transactional
    public Clienti update(Long id, Clienti cliente) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        cliente.setId(id);
        return clientiRepository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        clientiRepository.deleteById(id);
    }
}
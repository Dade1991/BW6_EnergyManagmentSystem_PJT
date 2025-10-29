package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientiService {

    private final ClienteRepository clientiRepository;

    public ClientiService(ClienteRepository clientiRepository) {
        this.clientiRepository = clientiRepository;
    }

    public List<Cliente> findAll() {
        return clientiRepository.findAll();
    }

    public Cliente findById(UUID id) {
        return clientiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato: " + id));
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        return clientiRepository.save(cliente);
    }

    @Transactional
    public Cliente update(UUID id, Cliente cliente) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        cliente.setId(id);
        return clientiRepository.save(cliente);
    }

    @Transactional
    public void delete(UUID id) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        clientiRepository.deleteById(id);
    }
}
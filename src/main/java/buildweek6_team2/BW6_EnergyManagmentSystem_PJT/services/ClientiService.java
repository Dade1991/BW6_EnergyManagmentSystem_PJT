package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

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
    public Clienti update(UUID id, Clienti cliente) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        cliente.setIdCliente(id);
        return clientiRepository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        if (!clientiRepository.existsById(id)) {
            throw new RuntimeException("Cliente non trovato: " + id);
        }
        clientiRepository.deleteById(id);
    }

    public Clienti save(@Valid Clienti cliente) {
        return cliente;
    }

    public boolean deleteById(Long id) {
        return false;
    }
}
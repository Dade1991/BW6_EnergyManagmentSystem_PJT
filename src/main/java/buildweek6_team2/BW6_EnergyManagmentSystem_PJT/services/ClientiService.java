package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Indirizzo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.ClienteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientiService {

    @Autowired
    private ClienteRepository clientiRepository;

    // FIND ALL
    public Page<Cliente> findAllClienti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        sortBy = "nomeContatto";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.clientiRepository.findAll(pageable);
    }

    // SAVE

    public Cliente saveClienti(ClienteDTO payload) {
        this.clientiRepository.findByEmail(payload.email()).ifPresent(cliente -> {
            throw new BadRequestException("The email " + cliente.getEmail() + " has not been found. Try again.");
        });
        this.clientiRepository.findByPEC(payload.pec()).ifPresent(cliente -> {
            throw new BadRequestException("The PEC " + cliente.getPec() + " has not been found. Try again.");
        });
        this.clientiRepository.findByPartitaIva(payload.partitaIva()).ifPresent(cliente -> {
            throw new BadRequestException("The P.I. code " + cliente.getPartitaIva() + " has not been found. Try again.");
        });

        Cliente newCliente = new Cliente();

        newCliente.setRagioneSociale(payload.ragioneSociale());
        newCliente.setPartitaIva(payload.partitaIva());
        newCliente.setEmail(payload.email());
        newCliente.setDataInserimento(payload.dataInserimento());
        newCliente.setDataUltimoContatto(payload.dataUltimoContatto());
        newCliente.setFatturatoAnnuale(payload.fatturatoAnnuale());
        newCliente.setPec(payload.pec());
        newCliente.setTelefono(payload.telefono());
        newCliente.setLogoAziendale("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2Fux-catch-all--438115870007811792%2F&psig=AOvVaw1yDoHvyEoP77H9UW8Q0B5M&ust=1761834169949000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCz6vHNyZADFQAAAAAdAAAAABAE");
        newCliente.setEmailContatto(payload.emailContatto());
        newCliente.setNomeContatto(payload.nomeContatto());
        newCliente.setCognomeContatto(payload.cognomeContatto());
        newCliente.setTelefonoContatto(payload.telefonoContatto());
        newCliente.setTipoCliente(payload.tipoCliente());
        newCliente.setIndirizzoLegale(payload.indirizzoSedeLegale());
        newCliente.setIndirizzoOperativo(payload.indirizzoSedeOperativo());

        return this.clientiRepository.save(newCliente);
    }

    // FIND BY ID & UPDATE

    @Transactional
    public Cliente findClientiByIdAndUpdate(Long clienteId, ClienteDTO payload) {
        Cliente found = this.findClientiById(clienteId);

        if (!found.getEmail().equals(payload.email())) {
            this.clientiRepository.findByEmail(payload.email()).ifPresent(cliente -> {
                throw new BadRequestException("The email " + cliente.getEmail() + " has not been found. Try again.");
            });
        }
        if (!found.getPec().equals(payload.pec())) {
            this.clientiRepository.findByPEC(payload.pec()).ifPresent(cliente -> {
                throw new BadRequestException("The PEC " + cliente.getPec() + " has not been found. Try again.");
            });
        }
        if (!found.getPartitaIva().equals(payload.partitaIva())) {
            this.clientiRepository.findByPartitaIva(payload.partitaIva()).ifPresent(cliente -> {
                throw new BadRequestException("The P.I. code " + cliente.getPartitaIva() + " has not been found. Try again.");
            });
        }

        Indirizzo indirizzo1Found = ;
        Indirizzo indirizzo2Found =;

        found.setRagioneSociale(payload.ragioneSociale());
        found.setPartitaIva(payload.partitaIva());
        found.setEmail(payload.email());
        found.setDataInserimento(payload.dataInserimento());
        found.setDataUltimoContatto(payload.dataUltimoContatto());
        found.setFatturatoAnnuale(payload.fatturatoAnnuale());
        found.setPec(payload.pec());
        found.setTelefono(payload.telefono());
        found.setEmailContatto(payload.emailContatto());
        found.setNomeContatto(payload.nomeContatto());
        found.setCognomeContatto(payload.cognomeContatto());
        found.setTelefonoContatto(payload.telefonoContatto());
        found.setTipoCliente(payload.tipoCliente());
        found.setIndirizzoLegale(payload.indirizzoSedeLegale());
        found.setIndirizzoOperativo(payload.indirizzoSedeOperativo());

        Cliente modifyCliente = this.clientiRepository.save(found);

        return modifyCliente;
    }

    // FIND BY ID & DELETE

    @Transactional
    public void deleteClienti(Long clienteId) {
        Cliente found = this.findClientiById(clienteId);
        this.clientiRepository.delete(found);
    }

    // FIND BY EMAIL

    public Cliente findClientiByEmail(String email) {
        return this.clientiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " has not been found."));
    }

    // FIND BY ID

    public Cliente findClientiById(Long clienteId) {
        return this.clientiRepository.findById(clienteId).orElseThrow(() -> new IdNotFoundException("L'utente con ID: " + clienteId + " non è stato trovato"));
    }

    // TODO: Aggiungere altri metodi per ordinamento e filtro clienti
}
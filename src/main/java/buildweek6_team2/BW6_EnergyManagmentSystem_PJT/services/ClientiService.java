package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.ClienteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.ClienteRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List.*;

@Service
public class ClientiService {

    @Autowired
    private ClienteRepository clientiRepository;
    
    private static Specification<Cliente> filtraPer(Double fatturatoAnnuale, LocalDate dataInserimento, LocalDate dataUltimoContatto, String nome) {
        return (root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (fatturatoAnnuale != null) {
                predicates.add(criteriaBuilder.equal(root.get("fatturatoAnnuale"), fatturatoAnnuale));
            }
            if (dataInserimento != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataInserimento"), dataInserimento));
            }
            if (dataUltimoContatto != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }
            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ragioneSociale")), "%" + nome.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Cliente> trovaClienti(
            Double fatturatoAnnuale,
            LocalDate dataInserimento,
            LocalDate dataUltimoContatto,
            String nome,
            int pagina,
            int dimensione,
            String ordinaPer) {
        if (dimensione > 50) dimensione = 50;
        Pageable pageable = PageRequest.of(pagina, dimensione, Sort.by(ordinaPer));
        Specification<Cliente> specifica = filtraPer(fatturatoAnnuale, dataInserimento, dataUltimoContatto, nome);
        return clientiRepository.findAll(specifica, pageable);
    }

    public Cliente salvaCliente(ClienteDTO payload) {
        this.clientiRepository.findByEmail(payload.email()).ifPresent(cliente -> {
            throw new BadRequestException("L'email " + cliente.getEmail() + " è già in uso.");
        });
        this.clientiRepository.findByPEC(payload.pec()).ifPresent(cliente -> {
            throw new BadRequestException("La PEC " + cliente.getPec() + " è già in uso.");
        });
        this.clientiRepository.findByPartitaIva(payload.partitaIva()).ifPresent(cliente -> {
            throw new BadRequestException("La Partita IVA " + cliente.getPartitaIva() + " è già in uso.");
        });

        Cliente nuovoCliente = new Cliente();

        nuovoCliente.setRagioneSociale(payload.ragioneSociale());
        nuovoCliente.setPartitaIva(payload.partitaIva());
        nuovoCliente.setEmail(payload.email());
        nuovoCliente.setDataInserimento(payload.dataInserimento());
        nuovoCliente.setDataUltimoContatto(payload.dataUltimoContatto());
        nuovoCliente.setFatturatoAnnuale(payload.fatturatoAnnuale());
        nuovoCliente.setPec(payload.pec());
        nuovoCliente.setTelefono(payload.telefono());
        nuovoCliente.setLogoAziendale("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2Fux-catch-all--438115870007811792%2F&psig=AOvVaw1yDoHvyEoP77H9UW8Q0B5M&ust=1761834169949000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCz6vHNyZADFQAAAAAdAAAAABAE");
        nuovoCliente.setEmailContatto(payload.emailContatto());
        nuovoCliente.setNomeContatto(payload.nomeContatto());
        nuovoCliente.setCognomeContatto(payload.cognomeContatto());
        nuovoCliente.setTelefonoContatto(payload.telefonoContatto());
        nuovoCliente.setTipoCliente(payload.tipoCliente());
        nuovoCliente.setIndirizzoSede1(payload.indirizzoSede1());
        nuovoCliente.setIndirizzoSede2(payload.indirizzoSede1());
        nuovoCliente.setProvinciaSedeLegale(payload.provinciaSedeLegale());

        Cliente clienteSalvato = this.clientiRepository.save(nuovoCliente);

        return clienteSalvato;
    }

    // FIND BY ID & UPDATE

    @Transactional
    public Cliente trovaClientePerIdEAggiorna(Long clienteId, ClienteDTO payload) {
        Cliente clienteTrovato = this.trovaClientePerId(clienteId);

        if (!clienteTrovato.getEmail().equals(payload.email())) {
            this.clientiRepository.findByEmail(payload.email()).ifPresent(cliente -> {
                throw new BadRequestException("L'email " + payload.email() + " è già utilizzata da un altro cliente.");
            });
        }
        if (!clienteTrovato.getPec().equals(payload.pec())) {
            this.clientiRepository.findByPEC(payload.pec()).ifPresent(cliente -> {
                throw new BadRequestException("La PEC " + payload.pec() + " è già utilizzata da un altro cliente.");
            });
        }
        if (!clienteTrovato.getPartitaIva().equals(payload.partitaIva())) {
            this.clientiRepository.findByPartitaIva(payload.partitaIva()).ifPresent(cliente -> {
                throw new BadRequestException("La Partita IVA " + payload.partitaIva() + " è già utilizzata da un altro cliente.");
            });
        }

        clienteTrovato.setRagioneSociale(payload.ragioneSociale());
        clienteTrovato.setPartitaIva(payload.partitaIva());
        clienteTrovato.setEmail(payload.email());
        clienteTrovato.setDataInserimento(payload.dataInserimento());
        clienteTrovato.setDataUltimoContatto(payload.dataUltimoContatto());
        clienteTrovato.setFatturatoAnnuale(payload.fatturatoAnnuale());
        clienteTrovato.setPec(payload.pec());
        clienteTrovato.setTelefono(payload.telefono());
        clienteTrovato.setEmailContatto(payload.emailContatto());
        clienteTrovato.setNomeContatto(payload.nomeContatto());
        clienteTrovato.setCognomeContatto(payload.cognomeContatto());
        clienteTrovato.setTelefonoContatto(payload.telefonoContatto());
        clienteTrovato.setTipoCliente(payload.tipoCliente());
        clienteTrovato.setIndirizzoSede1(payload.indirizzoSede1());
        clienteTrovato.setIndirizzoSede2(payload.indirizzoSede1());
        clienteTrovato.setProvinciaSedeLegale(payload.provinciaSedeLegale());

        Cliente clienteModificato = this.clientiRepository.save(clienteTrovato);

        return clienteModificato;
    }

    // FIND BY ID & DELETE

    @Transactional
    public void eliminaClientePerId(Long clienteId) {
        Cliente clienteTrovato = this.trovaClientePerId(clienteId);
        this.clientiRepository.delete(clienteTrovato);
    }

    // FIND BY EMAIL

    public Cliente trovaClientePerEmail(String email) {
        return this.clientiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Cliente con email " + email + " non trovato."));
    }

    // FIND BY ID

    public Cliente trovaClientePerId(Long clienteId) {
        return this.clientiRepository.findById(clienteId).orElseThrow(() -> new IdNotFoundException("L'utente con ID: " + clienteId + " non è stato trovato"));
    }
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.FatturaDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.FatturaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private StatoFatturaService statoFatturaService;

    @Autowired
    private ClientiService clientiService;

    // FIND ALL

    public Page<Fattura> findAllFatture(int numeroPagina, int dimensionePagina, String ordinaPer,
                                        Cliente cliente, StatoFattura stato, LocalDate data,
                                        Integer anno, Double minImporto, Double maxImporto) {
        if (dimensionePagina > 50) dimensionePagina = 50;
        Pageable pageable = PageRequest.of(numeroPagina, dimensionePagina, Sort.by(ordinaPer).ascending());
        Specification<Fattura> specifica = SpecificheFattura.filtraPer(cliente, stato, data, anno, minImporto, maxImporto);
        return this.fatturaRepository.findAll(specifica, pageable);
    }

    // SAVE

    public Fattura saveFattura(FatturaDTO payload) {
        this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {
            throw new BadRequestException("The invoice n°: " + fattura.getNumero() + " is already in use.");
        });

        Cliente found = this.clientiService.trovaClientePerId(payload.idCliente());

        StatoFattura initialState = this.statoFatturaService.findStatoFatturaByStato("EMESSA");

        Fattura newFattura = new Fattura(
                payload.data(),
                payload.importo(),
                payload.numero(),
                found
        );

        newFattura.setData(payload.data());
        newFattura.setImporto(payload.importo());
        newFattura.setNumero(payload.numero());
        newFattura.setStatoFattura(List.of(initialState));

        Fattura savedFattura = this.fatturaRepository.save(newFattura);

        return savedFattura;
    }

    // FIND BY ID & UPDATE

    public Fattura findFatturaByIdAndUpdate(Long idFattura,
                                            FatturaDTO payload) {
        Fattura found = this.findFatturaById(idFattura);

        if (!found.getNumero().equals(payload.numero())) {
            this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {
                throw new BadRequestException("ID status '" + payload.numero() + "' is already in use.");
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
        return this.fatturaRepository.findById(idFattura)
                .orElseThrow(() -> new IdNotFoundException("The invoice n°: " + idFattura + " has not been found."));
    }

    // Specifiche Fattura

    private static class SpecificheFattura {
        public static Specification<Fattura> filtraPer(Cliente cliente, StatoFattura stato, LocalDate data, Integer anno, Double minImporto, Double maxImporto) {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (cliente != null) {
                    predicates.add(criteriaBuilder.equal(root.get("cliente"), cliente));
                }
                if (stato != null) {
                    predicates.add(criteriaBuilder.equal(root.get("statoFattura"), stato));
                }
                if (data != null) {
                    predicates.add(criteriaBuilder.equal(root.get("data"), data));
                }
                if (anno != null) {
                    predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("data")), anno));
                }
                if (minImporto != null && maxImporto != null) {
                    predicates.add(criteriaBuilder.between(root.get("importo"), minImporto, maxImporto));
                } else if (minImporto != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("importo"), minImporto));
                } else if (maxImporto != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("importo"), maxImporto));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Cliente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Fattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.StatoFattura;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClientiService clientiService;

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

    public Page<Fattura> trovaTutteLeFatture(int numeroPagina, int dimensionePagina, String ordinaPer,
                                        Cliente cliente, StatoFattura stato, LocalDate data,
                                        Integer anno, Double minImporto, Double maxImporto) {
        if (dimensionePagina > 50) dimensionePagina = 50;
        Pageable pageable = PageRequest.of(numeroPagina, dimensionePagina, Sort.by(ordinaPer).ascending());
        Specification<Fattura> specifica = SpecificheFattura.filtraPer(cliente, stato, data, anno, minImporto, maxImporto);
        return this.fatturaRepository.findAll(specifica, pageable);
    }


    // SAVE

    public Fattura trovaPerId(Long id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata: " + id));
    }


    public Fattura salvaFattura(FatturaDTO payload) {

        this.fatturaRepository.findByNumero(payload.numero()).ifPresent(fattura -> {
            throw new BadRequestException("La fattura con numero " + fattura.getNumero() + " esiste già!");
        });

        // Recupero il cliente tramite il suo ID
        Cliente cliente = clientiService.trovaClientePerId(payload.idCliente());

        Fattura nuovaFattura = new Fattura();

        nuovaFattura.setData(LocalDate.now());
        nuovaFattura.setImporto(payload.importo());
        nuovaFattura.setNumero(payload.numero());
        nuovaFattura.setCliente(cliente);

        return fatturaRepository.save(nuovaFattura);
    }

    @Transactional
    public Fattura aggiornaFattura(Long id, Fattura fattura) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fattura.setIdFattura(id);
        return fatturaRepository.save(fattura);
    }

    @Transactional
    public void eliminaFattura(Long id) {
        if (!fatturaRepository.existsById(id)) {
            throw new RuntimeException("Fattura non trovata: " + id);
        }
        fatturaRepository.deleteById(id);
    }
}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fatture")
@Data
@NoArgsConstructor
@ToString
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id_fattura", updatable = false, nullable = false)
    private Long idFattura;

    public void setIdFattura(Long idFattura) {
        this.idFattura = idFattura;
    }

    private LocalDate data;
    private Double importo;
    private String numero;

    @ManyToMany
    @JoinTable(name = "stato_fattura",
            joinColumns = @JoinColumn(name = "fatturaId"),
            inverseJoinColumns = @JoinColumn(name = "statoFatturaId"))
    private List<StatoFattura> statoFattura = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Cliente cliente;

    public Fattura(LocalDate data, Double importo, String numero, Cliente cliente) {
        this.data = data;
        this.importo = importo;
        this.numero = numero;
        this.cliente = cliente;
    }
}
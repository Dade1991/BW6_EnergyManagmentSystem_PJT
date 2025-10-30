package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private LocalDate data;
    private Double importo;
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stato_fattura_id")
    private StatoFattura statoFattura;

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
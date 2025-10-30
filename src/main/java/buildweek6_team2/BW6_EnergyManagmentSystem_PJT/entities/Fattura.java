package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "fatture")
@Setter
@Getter
@NoArgsConstructor
public class Fattura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id_fattura", updatable = false, nullable = false)
    private Long idFattura;

    private LocalDate data;
    private Double importo;
    private String numero;

    @OneToMany(mappedBy = "statoFattura")
    @Column(name = "stato_fattura")
    private StatoFattura statoFattura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Cliente cliente;
}

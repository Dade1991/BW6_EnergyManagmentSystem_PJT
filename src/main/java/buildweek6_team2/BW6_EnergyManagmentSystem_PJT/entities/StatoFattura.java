package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stato_fatture")
@Data
@NoArgsConstructor
@ToString
public class StatoFattura {
    @OneToMany(mappedBy = "statoFattura")
    List<Fattura> listaFatture = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stato_fattura")
    private Long idStatoFattura;
    private String stato;

    public StatoFattura(String stato) {
        this.stato = stato;
    }
}
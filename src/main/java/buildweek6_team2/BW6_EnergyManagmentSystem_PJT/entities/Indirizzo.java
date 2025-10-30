package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "indirizzi")
@Setter
@Getter
@NoArgsConstructor
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id_indirizzo")
    private Long idIndirizzo;

    private String via;
    private String civico;
    private String localita;
    private int cap;


    public Indirizzo(String via, String civico, String localita, int cap) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
    }
}

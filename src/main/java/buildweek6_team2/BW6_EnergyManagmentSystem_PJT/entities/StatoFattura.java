package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.enums.StatoFatturaType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "stato_fatture")
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_stato_fattura")
    private UUID idStatoFattura;

    @Enumerated(EnumType.STRING)
    private StatoFatturaType stato;

    public StatoFattura() {
    }

    public StatoFattura(StatoFatturaType stato) {
        this.stato = stato;
    }

    public UUID getIdStatoFattura() {
        return idStatoFattura;
    }


    public StatoFatturaType getStato() {
        return stato;
    }

    public void setStato(StatoFatturaType stato) {
        this.stato = stato;
    }
}
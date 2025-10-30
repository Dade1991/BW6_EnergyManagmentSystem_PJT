package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stato_fatture")
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stato_fattura")
    private Long idStatoFattura;

    @Enumerated(EnumType.STRING)
    private String stato;

    public StatoFattura() {
    }

    public StatoFattura(String stato) {
        this.stato = stato;
    }

    public Long getIdStatoFattura() {
        return idStatoFattura;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
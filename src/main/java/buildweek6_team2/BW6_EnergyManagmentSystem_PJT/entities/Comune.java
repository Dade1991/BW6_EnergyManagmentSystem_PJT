package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comuni")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comune")
    private Long idComune;

    @Column(name = "nome_comune")
    private String nomeComune;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    public Comune() {
    }

    public Comune(String nomeComune, Provincia provincia) {
        this.nomeComune = nomeComune;
        this.provincia = provincia;
    }

    public Long getIdComune() {
        return idComune;
    }


    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
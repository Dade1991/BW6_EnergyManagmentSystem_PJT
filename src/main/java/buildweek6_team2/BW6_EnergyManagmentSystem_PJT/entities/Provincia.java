package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "province")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Long idProvincia;

    @Column(name = "nome_provincia")
    private String nomeProvincia;

    private String sigla;

    private String regione;

    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    private List<Comune> comuni;

    public Provincia() {
    }

    public Provincia(String nomeProvincia, String sigla, String regione) {
        this.nomeProvincia = nomeProvincia;
        this.sigla = sigla;
        this.regione = regione;
    }

    public Long getIdProvincia() {
        return idProvincia;
    }


    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public List<Comune> getComuni() {
        return comuni;
    }

    public void setComuni(List<Comune> comuni) {
        this.comuni = comuni;
    }
}
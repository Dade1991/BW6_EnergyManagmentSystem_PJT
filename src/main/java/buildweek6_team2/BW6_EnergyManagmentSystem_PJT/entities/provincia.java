package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Long idProvincia;
    
    @Column(name = "nome_provincia")
    private String nomeProvincia;
    
    @Column(name = "sigla")
    private String sigla;
    
    @Column(name = "regione")
    private String regione;
    
    // Constructors
    public Provincia() {
    }
    
    public Provincia(Long idProvincia, String nomeProvincia, String sigla, String regione) {
        this.idProvincia = idProvincia;
        this.nomeProvincia = nomeProvincia;
        this.sigla = sigla;
        this.regione = regione;
    }
    
    // Getters and Setters
    public Long getIdProvincia() {
        return idProvincia;
    }
    
    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
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
}


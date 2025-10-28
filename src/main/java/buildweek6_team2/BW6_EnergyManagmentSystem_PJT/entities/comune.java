package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comune")
public class comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comune")
    private Long idComune;
    
    @Column(name = "provincia_id")
    private Long provinciaId;
    
    @Column(name = "nome_del_comune")
    private String nomeDelComune;
    
    @Column(name = "nome_provincia")
    private String nomeProvincia;
    
    
    public comune() {
    }
    
    public comune(Long idComune, Long provinciaId, String nomeDelComune, String nomeProvincia) {
        this.idComune = idComune;
        this.provinciaId = provinciaId;
        this.nomeDelComune = nomeDelComune;
        this.nomeProvincia = nomeProvincia;
    }
    
    
    public Long getIdComune() {
        return idComune;
    }
    
    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }
    
    public Long getProvinciaId() {
        return provinciaId;
    }
    
    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }
    
    public String getNomeDelComune() {
        return nomeDelComune;
    }
    
    public void setNomeDelComune(String nomeDelComune) {
        this.nomeDelComune = nomeDelComune;
    }
    
    public String getNomeProvincia() {
        return nomeProvincia;
    }
    
    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }
}

package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "statofatture")
public class statofatture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstatofatture")
    private Long idstatofatture;
    
    @Column(name = "stato")
    private String stato;
    
    // Constructors
    public statofatture() {
    }
    
    public statofatture(Long idstatofatture, String stato) {
        this.idstatofatture = idstatofatture;
        this.stato = stato;
    }
    
    // Getters and Setters
    public Long getIdstatofatture() {
        return idstatofatture;
    }
    
    public void setIdstatofatture(Long idstatofatture) {
        this.idstatofatture = idstatofatture;
    }
    
    public String getStato() {
        return stato;
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
}

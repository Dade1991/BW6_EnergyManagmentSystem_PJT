package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utenti_Ruoli {
    @ManyToOne
    @JoinColumn(name = "utente")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "ruolo")
    private Ruolo ruolo;
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.enums.TipoRuolo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ruolo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID ruoloId;
    @Enumerated(EnumType.STRING)
    private TipoRuolo tipoRuolo;
    @ManyToMany(mappedBy = "ruolo")
    private List<Utente> ListaUtenteRuoli = new ArrayList<>();

    public Ruolo(TipoRuolo tipoRuolo) {
        this.tipoRuolo = tipoRuolo;
    }
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "ruolo_id")
    private Long ruoloId;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ruolo")
    private String tipoRuolo;

    @ManyToMany(mappedBy = "ruolo")
    private List<Utente> ListaUtenteRuoli = new ArrayList<>();

    public Ruolo(String tipoRuolo) {
        this.tipoRuolo = tipoRuolo;
    }
}
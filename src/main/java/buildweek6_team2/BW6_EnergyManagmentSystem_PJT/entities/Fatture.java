package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fatture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fatture {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "idFattura", updatable = false, nullable = false)
    private UUID idFattura;

    private LocalDate data;
    private Double importo;
    private String numero;

    @Column(name = "statId")
    private UUID statId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Clienti cliente;
}

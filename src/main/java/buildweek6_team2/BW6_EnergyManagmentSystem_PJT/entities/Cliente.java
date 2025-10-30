package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clienti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "idCliente", updatable = false, nullable = false)
    private Long clienteId;

    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private Double fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    @OneToOne
    @JoinColumn(name = "indirizzoLegale")
    private Indirizzo indirizzoLegale;
    @OneToOne
    @JoinColumn(name = "indirizzoOperativo")
    private Indirizzo indirizzoOperativo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fattura> fatture = new HashSet<>();
}

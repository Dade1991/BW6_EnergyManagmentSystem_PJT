package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clienti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clienti {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "idCliente", updatable = false, nullable = false)
    private UUID idCliente;

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
    private Boolean logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    private String indirizzoSede1;
    private String indirizzoSede2;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fatture> fatture = new HashSet<>();
}

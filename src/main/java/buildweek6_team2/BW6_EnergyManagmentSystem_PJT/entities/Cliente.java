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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id_cliente", updatable = false, nullable = false)
    private Long clienteId;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva")
    private String partitaIva;
    private String email;
    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale")
    private Double fatturatoAnnuale;
    private String pec;
    private String telefono;
    @Column(name = "email_contatto")
    private String emailContatto;
    @Column(name = "nome_contatto")
    private String nomeContatto;
    @Column(name = "cognome_contatto")
    private String cognomeContatto;
    @Column(name = "telefono_contatto")
    private String telefonoContatto;
    @Column(name = "logo_aziendale")
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

    public Cliente(String ragioneSociale, String partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, Double fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, String logoAziendale, TipoCliente tipoCliente, Indirizzo indirizzoLegale, Indirizzo indirizzoOperativo) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = logoAziendale;
        this.tipoCliente = tipoCliente;
        this.indirizzoLegale = indirizzoLegale;
        this.indirizzoOperativo = indirizzoOperativo;
    }
}

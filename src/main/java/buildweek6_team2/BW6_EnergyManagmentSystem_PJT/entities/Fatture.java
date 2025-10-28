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
    private Long idFattura;

    private LocalDate data;
    private Double importo;
    private String numero;

    @Column(name = "statId")
    private UUID statId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Clienti cliente;

    public Long getIdFattura() {
        return idFattura;
    }

    public void setIdFattura(Long idFattura) {
        this.idFattura = idFattura;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public UUID getStatId() {
        return statId;
    }

    public void setStatId(UUID statId) {
        this.statId = statId;
    }

    public Clienti getCliente() {
        return cliente;
    }

    public void setCliente(Clienti cliente) {
        this.cliente = cliente;
    }
}

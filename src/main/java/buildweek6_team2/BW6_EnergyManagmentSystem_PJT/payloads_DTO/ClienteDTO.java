package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.enums.TipoCliente;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClienteDTO(
        @NotBlank(message = "La ragione sociale è obbligatoria")
        @Size(min = 2, max = 30, message = "La ragione sociale deve avere un minimo di due caratteri e un massimo di 30")
        String ragioneSociale,
        @NotBlank(message = "La partita IVA è obbligatoria")
        @Size(min = 11, max = 11, message = "La partita IVA deve essere composta da 11 cifre")
        String partitaIva,
        @NotBlank(message = "L'e-mail è obbligatoria")
        @Email(message = "L'indirizzo e-mail non è nel formato giusto")
        String email,
        @NotBlank(message = "La data di inserimento è obbligatoria")
        LocalDate dataInserimento,
        @PastOrPresent(message = "La data di ultimo contatto non può essere nel futuro")
        LocalDate dataUltimoContatto,
        @NotBlank(message = "Il fatturato annuale è obbligatorio")
        @PositiveOrZero(message = "Il fatturato annuale non può essere negativo")
        Double fatturatoAnnuale,
        @Email(message = "L'indirizzo PEC non è nel formato giusto")
        String pec,
        @NotBlank(message = "Il numero di telefono è obbligatorio")
        @Pattern(regexp = "^[0-9\\s\\-()]{5,20}$", message = "Il telefono non è nel formato corretto")
        String telefono,
        @NotBlank(message = "È obbligatorio specificare la presenza del logo aziendale")
        String logoAziendale,
        @NotBlank(message = "L'e-mail del contatto è obbligatoria")
        @Email(message = "L'e-mail del contatto non è nel formato giusto")
        String emailContatto,
        @NotBlank(message = "Il nome del contatto è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome del contatto deve avere un minimo di due caratteri e un massimo di 20")
        String nomeContatto,
        @NotBlank(message = "Il cognome del contatto è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome del contatto deve avere un minimo di due caratteri e un massimo di 30")
        String cognomeContatto,
        @NotBlank(message = "Il numero di telefono del contatto è obbligatorio")
        @Pattern(regexp = "^[0-9\\s\\-()]{5,20}$", message = "Il telefono del contatto non è nel formato corretto")
        String telefonoContatto,
        @NotNull(message = "Il tipo di cliente è obbligatorio")
        TipoCliente tipoCliente,
        @NotBlank(message = "L'indirizzo della sede legale è obbligatorio")
        String indirizzoSede1,
        String indirizzoSede2
) {
}

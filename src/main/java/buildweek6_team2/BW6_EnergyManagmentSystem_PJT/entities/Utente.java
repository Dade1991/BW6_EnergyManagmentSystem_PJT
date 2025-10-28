package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID utenteId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cognome;
    @Column(name = "avatar_url", nullable = false, unique = true)
    private String avatarURL;

    @Column(nullable = false, unique = true)
    @OneToMany(mappedBy = "utente")
    private List<Utenti_Ruoli> listaUtenteRuoli = new ArrayList<>();

    public Utente(String username,
                  String email,
                  String password,
                  String nome,
                  String cognome,
                  Ruolo ruolo,
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    //Metodi
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority());
    }

}
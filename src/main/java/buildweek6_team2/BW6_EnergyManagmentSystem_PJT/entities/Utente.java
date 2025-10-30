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
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long utenteId;
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
    @ManyToMany
    @JoinTable(name = "utente_ruoli",
            joinColumns = @JoinColumn(name = "utenteId"),
            inverseJoinColumns = @JoinColumn(name = "ruoloId"))
    private List<Ruolo> ruolo = new ArrayList<>();

    public Utente(
            String username,
            String email,
            String password,
            String nome,
            String cognome
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
        return ruolo.stream().map(ruolo -> new SimpleGrantedAuthority(ruolo.getTipoRuolo().name())).collect(Collectors.toList());
    }
}
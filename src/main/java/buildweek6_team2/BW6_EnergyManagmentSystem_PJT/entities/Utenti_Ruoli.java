package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utenti_Ruoli {
    @ManyToOne
    @JoinColumn(name = "utente")
    @Setter(AccessLevel.NONE)
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "ruolo")
    private Ruolo ruolo;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(this.ruolo.getTipoRuolo().name()));
//    }

}
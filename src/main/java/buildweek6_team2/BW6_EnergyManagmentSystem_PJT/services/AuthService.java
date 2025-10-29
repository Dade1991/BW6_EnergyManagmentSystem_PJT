package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.UnauthorizedException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.LoginDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtentiService utentiService;
    @Autowired
    private PasswordEncoder bCrypt;
    @Autowired
    private JWTTools jwtTools;

    public String checkAndCreateToken(LoginDTO bodyLogin){
        // Controllo delle credenziali d'accesso
        Utente utenteFound = this.utentiService.findByEmail(bodyLogin.email());

        if(bCrypt.matches(bodyLogin.Password(), utenteFound.getPassword())){
            return jwtTools.createToken(utenteFound);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }

}

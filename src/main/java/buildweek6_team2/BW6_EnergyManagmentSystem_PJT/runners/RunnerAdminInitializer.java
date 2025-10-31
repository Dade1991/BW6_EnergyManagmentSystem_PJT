package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.runners;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.RuoloRepository;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RunnerAdminInitializer implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private PasswordEncoder bCrypt;


    @Override
    public void run(String... args) throws Exception {

        //Creazione del ruolo ADMIN se non esiste
        Ruolo adminRole = this.ruoloRepository.findBytipoRuolo("ADMIN").orElseGet(() -> this.ruoloRepository.save(new Ruolo("ADMIN")));

        //Creazione del ruolo USER se non esiste
        Ruolo userRole = this.ruoloRepository.findBytipoRuolo("USER").orElseGet(() -> this.ruoloRepository.save(new Ruolo("USER")));

        //Creazione dell'utente ADMIN

        if (this.utenteRepository.findByEmail(("admin@admin.com")).isEmpty()) {
            Utente admin = new Utente("admin", "admin@admin.com", bCrypt.encode("admin1234"), "Claudio", "Postiglione");
            admin.setAvatarURL("https://ui-avatars.com/api/?name=" + admin.getNome() + "+" + admin.getCognome());
            admin.getRuolo().add(adminRole);
            this.utenteRepository.save(admin);
        }

        //Creazione dell'utente USER
        if(this.utenteRepository.findByEmail("user@demo.com").isEmpty()){
            Utente user = new Utente("user","user@demo.com", bCrypt.encode("user1234"),"Davide", "Braghi");
            user.setAvatarURL("https://ui-avatars.com/api/?name=" + user.getNome() + "+" + user.getCognome());
            user.getRuolo().add(userRole);
            this.utenteRepository.save(user);
        }

        if(this.utenteRepository.findByEmail("user2@demo.com").isEmpty()){
            Utente user = new Utente("user2","user2@demo.com", bCrypt.encode("user1234"),"Antimo", "Mandorino");
            user.setAvatarURL("https://ui-avatars.com/api/?name=" + user.getNome() + "+" + user.getCognome());
            user.getRuolo().add(userRole);
            this.utenteRepository.save(user);
        }

        if(this.utenteRepository.findByEmail("user3@demo.com").isEmpty()){
            Utente user = new Utente("user3","user3@demo.com", bCrypt.encode("user1234"),"Alessandro", "Di Martino");
            user.setAvatarURL("https://ui-avatars.com/api/?name=" + user.getNome() + "+" + user.getCognome());
            user.getRuolo().add(userRole);
            this.utenteRepository.save(user);
        }

        if(this.utenteRepository.findByEmail("user4@demo.com").isEmpty()){
            Utente user = new Utente("user4","user4@demo.com", bCrypt.encode("user1234"),"Oleksandr", "Kozelkivskyy");
            user.setAvatarURL("https://ui-avatars.com/api/?name=" + user.getNome() + "+" + user.getCognome());
            user.getRuolo().add(userRole);
            this.utenteRepository.save(user);
        }

    }
}

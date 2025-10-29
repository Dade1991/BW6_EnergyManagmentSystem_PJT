package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.UtenteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtentiService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    // FIND ALL

    public Page<Utente> findAllUtenti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        sortBy= "nome";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.utenteRepository.findAll(pageable);
    }

    // SAVE

    public Utente saveUtente(UtenteDTO payload) {
        this.utenteRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("The e-mail " + utente.getEmail() + " is already in use.");
        }
        );

        Utente newUtente = new Utente(payload.username(),
                payload.email(),
                bcrypt.encode(payload.password()),
                payload.nome(),
                payload.cognome(),
                payload.avatarURL(),
                payload.role() // <------- TBD
        );

        Utente savedUtente = this.utenteRepository.save(newUtente);

        log.info("The user with ID: " + " has been duly saved.");

        return savedUtente;
    }

    // FIND BY ID

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    // FIND BY ID & UPDATE

    public Utente findByIdAndUpdate(UUID utenteId, NewUtenteDTO payload) {
        Utente found = this.findById(utenteId);

        if(!found.getEmail().equals(payload.email())) {
            this.utenteRepository.findByEmail(payload.email()).ifPresent(utente-> {
                throw new BadRequestException("The e-mail " + utente.getEmail() + " is already in use. Try again.");
            }
            );
        }

        found.setUsername(payload.username());
        found.setEmail(payload.email());
        found.setPassword(payload.password());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setAvatarURL(payload.avatarURL);
        found.setRole(payload.role()); // <------- TBD

        Utente modifyUtente = this.utenteRepository.save(found);

        log.info("User with ID: " + modifyUtente.getId() + " has been duly updated.");

        return modifyUtente;
    }

    // FIND BY ID & DELETE

    public void findByIdAndDelete(UUID utenteId){
        Utente found = this.findById(utenteId);
        this.utenteRepository.delete(found);
    }

    // FIND BY EMAIL

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User with email " + email + " has not been found."));
    }

    public Utente findUtenteById(UUID utenteId){
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new IdNotFoundException("L'utente con ID: " + utenteId + " non è stato trovato"))
    }
}

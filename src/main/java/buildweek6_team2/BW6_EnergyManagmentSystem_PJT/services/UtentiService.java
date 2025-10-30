package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.enums.TipoRuolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.UtenteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.utenteRepository.findAll(pageable);
    }

    // SAVE

    public Utente saveUtenti(UtenteDTO payload) {
        this.utenteRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("The e-mail " + utente.getEmail() + " is already in use.");
        });

        Utente newUtente = new Utente(payload.username(),
                payload.email(),
                bcrypt.encode(payload.password()),
                payload.nome(),
                payload.cognome()
        );

        newUtente.setAvatarURL("https://ui-avatars.com/api/?name=" + payload.nome() + "+" + payload.cognome());
        newUtente.getRuolo().add(new Ruolo(TipoRuolo.ADMIN));
        newUtente.getRuolo().add(new Ruolo(TipoRuolo.USER));

        Utente savedUtente = this.utenteRepository.save(newUtente);

        log.info("The user with ID: " + " has been duly saved.");

        return savedUtente;
    }

    // FIND BY ID & UPDATE

    public Utente findUtentiByIdAndUpdate(Long utenteId, UtenteDTO payload) {
        Utente found = this.findUtentiById(utenteId);

        if (!found.getEmail().equals(payload.email())) {
            this.utenteRepository.findByEmail(payload.email()).ifPresent(utente -> {
                throw new BadRequestException("The email " + utente.getEmail() + " has not been found. Try again.");
            });
        }

        found.setUsername(payload.username());
        found.setEmail(payload.email());
        found.setPassword(payload.password());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setRuolo(payload.ruolo());

        Utente modifyUtente = this.utenteRepository.save(found);

        log.info("User with ID: " + modifyUtente.getUtenteId() + " has been duly updated.");

        return modifyUtente;
    }

    // FIND BY ID & DELETE

    public void findUtentiByIdAndDelete(Long utenteId) {
        Utente found = this.findUtentiById(utenteId);
        this.utenteRepository.delete(found);
    }

    // FIND BY EMAIL

    public Utente findUtentiByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " has not been found."));
    }

    // FIND BY ID

    public Utente findUtentiById(Long utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new IdNotFoundException("L'utente con ID: " + utenteId + " non è stato trovato"));
    }
}
package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Ruolo;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.BadRequestException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.IdNotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.exceptions.NotFoundException;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.UtenteDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.payloads_DTO.UtenteRoleDTO;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.repositories.UtenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class UtentiService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary getAvatarImage;
    @Autowired
    private RuoloService ruoloService;


    //Creo delle variabili per dei controlli sull'inserimento dell'avatar del profilo
    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_FORMAT = List.of("image/jpeg", "image/png");

    // FIND ALL (paginato)

    public Page<Utente> findAllUtenti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        sortBy = "nome";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.utenteRepository.findAll(pageable);
    }

    // FIND ALL (senza paginazione)

    public List<Utente> findAllUtentiWithoutPagination() {
        return this.utenteRepository.findAll();
    }

    // SAVE

    public Utente saveUtenti(UtenteDTO payload) {
        this.utenteRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("The e-mail " + utente.getEmail() + " is already in use.");
        });

        Ruolo ruoloFound = this.ruoloService.findByIdRuolo(0L);

        Utente newUtente = new Utente(payload.username(),
                payload.email(),
                bcrypt.encode(payload.password()),
                payload.nome(),
                payload.cognome()
        );


        newUtente.setAvatarURL("https://ui-avatars.com/api/?name=" + payload.nome() + "+" + payload.cognome());
        newUtente.getRuolo().add(ruoloFound);

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

        Utente modifyUtente = this.utenteRepository.save(found);

        log.info("User with ID: " + modifyUtente.getUtenteId() + " has been duly updated.");

        return modifyUtente;
    }

    // UPDATE del ruolo dell'utente
    public Utente UpdateRuoloUtente(Long utenteId, UtenteRoleDTO body){
        Utente utenteFound = this.findUtentiById(utenteId);
        Ruolo ruoloFound = this.ruoloService.findByIdRuolo(body.tipoRuolo());
        List<Ruolo> newRuolo = new ArrayList<>();
        newRuolo.add(ruoloFound);
        utenteFound.setRuolo(newRuolo);
        this.utenteRepository.save(utenteFound);
        log.info("| L'utente " + utenteFound.getNome() + " " + utenteFound.getCognome() + " con ID: " + utenteFound.getUtenteId() + " è stato modificato correttamente");
        return utenteFound;
    }

    // UPDATE dell'avatar del profilo
    public Utente uploadAvatarProfile(MultipartFile file, Long idUtente) {

        if (file.isEmpty()) throw new BadRequestException("File vuoto!");
        if (file.getSize() > MAX_SIZE)
            throw new BadRequestException("Attenzione, il file è superiore ai 5MB di dimensione");
        if (!(ALLOWED_FORMAT.contains(file.getContentType())))
            throw new BadRequestException("Attenzione, il formato non è corretto, deve essere del seguente tipo: (.jpeg) / (.png)");

        Utente utenteFound = this.findUtentiById(idUtente);

        try {
            //Cattura dell'URL dell'immagine
            Map resultMap = getAvatarImage.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) resultMap.get("url");

            //Salvataggio dell'immagine catturata
            utenteFound.setAvatarURL(imageUrl);
            this.utenteRepository.save(utenteFound);
            return utenteFound;
        } catch (Exception ex) {
            throw new BadRequestException("Errore nell'upload dell'immagine");
        }

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
package davidemancini.CapstoneBE.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import davidemancini.CapstoneBE.entities.TipoUtente;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyAlreadyExistingException;
import davidemancini.CapstoneBE.exceptions.MyBadRequest;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.UtentiDTO;
import davidemancini.CapstoneBE.repositories.TipoUtenteRepository;
import davidemancini.CapstoneBE.repositories.UtenteRepository;
import davidemancini.CapstoneBE.tools.MailGun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private TipoUtenteRepository tipoUtenteRepository;

    @Autowired
    private PasswordEncoder bCrypt;

    @Autowired
    private Cloudinary uploader;

    @Autowired
    private MailGun mailGun;

    private long MAX_SIZE = 5 * 1024 * 1024;
    private List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");

    public Utenti save(UtentiDTO body) {
        //CONTROLLO PRIMA SE L'EMAIL E USERNAME SONO GIA REGISTRATI
        if (utenteRepository.existsByEmail(body.email())) {
            throw new MyAlreadyExistingException("L'email " + body.email() + " è già in uso");
        }
        if (utenteRepository.existsByUsername(body.username())) {
            throw new MyAlreadyExistingException("L'username " + body.username() + " è già in uso");
        }
        TipoUtente user = tipoUtenteRepository.findByTipo("USER").orElseThrow(() -> new MyNotFoundException("Tipo utente non trovato"));
        Utenti newUtente = new Utenti(body.nome(), body.cognome(), body.email(), bCrypt.encode(body.password()), body.username(), user);
        Utenti salvato = utenteRepository.save(newUtente);
        mailGun.sendWelcomeEmail(salvato);
        return salvato;
    }

    public Utenti findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Utente con id " + id + " non trovato"));
    }

    public Utenti findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Utenti uploadAvatar(Utenti utente, MultipartFile file) {
        if (file.isEmpty()) throw new MyBadRequest("File vuoto");
        if (file.getSize() > MAX_SIZE) throw new MyBadRequest("File troppo grande, dimensione massima 5mb");
        if (!ALLOWED_TYPES.contains(file.getContentType())) throw new MyBadRequest("Formato non valido (jpeg o png");

        try {
            Map risultato = uploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String urlImg = (String) risultato.get("url");
            utente.setAvatar(urlImg);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return utenteRepository.save(utente);
    }

    public Utenti findByIdAndUpdate(UUID id, UtentiDTO body) {
        Utenti trovato = findById(id);
        trovato.setNome(body.nome());
        trovato.setCognome(body.cognome());
        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        return utenteRepository.save(trovato);
    }

    public void findByIdAndDelete(UUID id) {
        Utenti trovato = findById(id);
        utenteRepository.delete(trovato);
    }
}

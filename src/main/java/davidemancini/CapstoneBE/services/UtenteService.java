package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.TipoUtente;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.UtentiDTO;
import davidemancini.CapstoneBE.repositories.TipoUtenteRepository;
import davidemancini.CapstoneBE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private TipoUtenteRepository tipoUtenteRepository;

    @Autowired
    private PasswordEncoder bCrypt;


    public Utenti save(UtentiDTO body){
        TipoUtente found = tipoUtenteRepository.findByTipo(body.tipoUtente()).orElseThrow(()->new MyNotFoundException("Tipo di utente non trovato"));
        Utenti newUtente = new Utenti(body.nome(), body.cognome(), body.email(),bCrypt.encode(body.password()) , body.username(),found );
        return utenteRepository.save(newUtente);
    }
    public Utenti findById (UUID id){
       return utenteRepository.findById(id).orElseThrow(()-> new MyNotFoundException("Utente con id " + id+" non trovato"));
    }
}

package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.SessioneAstaDTO;
import davidemancini.CapstoneBE.repositories.SessioneAstaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessioneAstaService {
    @Autowired
    private SessioneAstaRepository sessioneAstaRepository;

    @Autowired
    private UtenteService utenteService;

    public SessioneAsta create (SessioneAstaDTO body){
        SessioneAsta newAsta = new SessioneAsta(body.nomeAsta(),body.numPartecipanti(), body.crediti());
        return sessioneAstaRepository.save(newAsta);
    }

    public SessioneAsta addUtente (UUID idAsta, UUID idUtente){
        SessioneAsta astaTrovata = sessioneAstaRepository.findById(idAsta).orElseThrow(()->new MyNotFoundException("Sessione asta con id " +idAsta+ " non trovata" ));
        Utenti utenteDaAggiungere = utenteService.findById(idUtente);
        //RECUPERO LISTA DI UTENTI DALL'ASTA TROVATA
        List<Utenti> listaUtenti = astaTrovata.getUtenti();
        //CONTROLLO SE NELLA LISTA DI QUELL'ASTA C'è GIA L'UTENTE DA AGGIUNGERE'
        if (!listaUtenti.contains(utenteDaAggiungere)){
            listaUtenti.add(utenteDaAggiungere);
        }
        astaTrovata.setUtenti(listaUtenti);
        return sessioneAstaRepository.save(astaTrovata);

    }

    public SessioneAsta findById(UUID id){
        return sessioneAstaRepository.findById(id).orElseThrow(()->new MyNotFoundException("Asta con id "+ id+" non trovata"));
    }
}

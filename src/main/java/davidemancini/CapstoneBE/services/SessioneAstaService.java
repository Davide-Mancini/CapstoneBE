package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.CasellaRosa;
import davidemancini.CapstoneBE.entities.RosaUtente;
import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.SessioneAstaDTO;
import davidemancini.CapstoneBE.repositories.RosaUtenteRepository;
import davidemancini.CapstoneBE.repositories.SessioneAstaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SessioneAstaService {
    @Autowired
    private SessioneAstaRepository sessioneAstaRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RosaUtenteRepository rosaUtenteRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public SessioneAsta create(SessioneAstaDTO body) {
        SessioneAsta newAsta = new SessioneAsta(body.nomeAsta(), body.numPartecipanti(), body.crediti());
        return sessioneAstaRepository.save(newAsta);
    }

    @Transactional
    public SessioneAsta addUtente(UUID idAsta, UUID idUtente) {
        SessioneAsta astaTrovata = sessioneAstaRepository.findById(idAsta).orElseThrow(() -> new MyNotFoundException("Sessione asta con id " + idAsta + " non trovata"));
        Utenti utenteDaAggiungere = utenteService.findById(idUtente);
        //RECUPERO LISTA DI UTENTI DALL'ASTA TROVATA
        List<Utenti> listaUtenti = astaTrovata.getUtenti();
        //CONTROLLO SE NELLA LISTA DI QUELL'ASTA C'è GIA L'UTENTE DA AGGIUNGERE'
        if (!listaUtenti.contains(utenteDaAggiungere)) {
            listaUtenti.add(utenteDaAggiungere);
        }
        astaTrovata.setUtenti(listaUtenti);
        List<SessioneAsta> listaSessioni = utenteDaAggiungere.getSessioni();
        listaSessioni.add(astaTrovata);
        if (utenteDaAggiungere.getRosa() == null) {
            RosaUtente nuovaRosa = new RosaUtente();
            nuovaRosa.setUtenti(utenteDaAggiungere);
            nuovaRosa.setCreditiResidui(300); // o il valore che usi

            // Crea le 25 caselle
            for (int i = 1; i <= 3; i++) {
                CasellaRosa c = new CasellaRosa("P", i);
                c.setRosa(nuovaRosa);
                nuovaRosa.getCaselle().add(c);
            }
            for (int i = 1; i <= 8; i++) {
                CasellaRosa c = new CasellaRosa("D", i);
                c.setRosa(nuovaRosa);
                nuovaRosa.getCaselle().add(c);
            }
            for (int i = 1; i <= 8; i++) {
                CasellaRosa c = new CasellaRosa("C", i);
                c.setRosa(nuovaRosa);
                nuovaRosa.getCaselle().add(c);
            }
            for (int i = 1; i <= 6; i++) {
                CasellaRosa c = new CasellaRosa("A", i);
                c.setRosa(nuovaRosa);
                nuovaRosa.getCaselle().add(c);
            }

            rosaUtenteRepository.save(nuovaRosa);
            utenteDaAggiungere.setRosa(nuovaRosa);
        }
        return sessioneAstaRepository.save(astaTrovata);
    }


    public SessioneAsta findById(UUID id) {
        return sessioneAstaRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Asta con id " + id + " non trovata"));
    }
}

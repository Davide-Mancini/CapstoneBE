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

        if (!astaTrovata.getUtenti().contains(utenteDaAggiungere)) {
            astaTrovata.getUtenti().add(utenteDaAggiungere);
        }


        if (!utenteDaAggiungere.getSessioni().contains(astaTrovata)) {
            utenteDaAggiungere.getSessioni().add(astaTrovata);
        }


        if (rosaUtenteRepository.existsByUtentiIdAndSessioneAstaId(idUtente, idAsta)) {
            System.out.println("Rosa già esistente per questo utente e questa asta → non ricreo.");
            return sessioneAstaRepository.save(astaTrovata);
        }


        RosaUtente nuovaRosa = new RosaUtente();
        nuovaRosa.setUtenti(utenteDaAggiungere);
        nuovaRosa.setCreditiResidui(astaTrovata.getCrediti());
        nuovaRosa.setSessioneAsta(astaTrovata);

        // Crea le 25 caselle
        for (int i = 1; i <= 3; i++) nuovaRosa.getCaselle().add(new CasellaRosa("P", i, nuovaRosa));
        for (int i = 1; i <= 8; i++) nuovaRosa.getCaselle().add(new CasellaRosa("D", i, nuovaRosa));
        for (int i = 1; i <= 8; i++) nuovaRosa.getCaselle().add(new CasellaRosa("C", i, nuovaRosa));
        for (int i = 1; i <= 6; i++) nuovaRosa.getCaselle().add(new CasellaRosa("A", i, nuovaRosa));

        rosaUtenteRepository.save(nuovaRosa);


        return sessioneAstaRepository.save(astaTrovata);
    }


    public SessioneAsta findById(UUID id) {
        return sessioneAstaRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Asta con id " + id + " non trovata"));
    }
}

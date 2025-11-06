package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.payloads.SessioneAstaDTO;
import davidemancini.CapstoneBE.repositories.SessioneAstaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessioneAstaService {
    @Autowired
    private SessioneAstaRepository sessioneAstaRepository;

    public SessioneAsta create (SessioneAstaDTO body){
        SessioneAsta newAsta = new SessioneAsta(body.nomeAsta(),body.numPartecipanti(), body.crediti());
        return sessioneAstaRepository.save(newAsta);
    }
}

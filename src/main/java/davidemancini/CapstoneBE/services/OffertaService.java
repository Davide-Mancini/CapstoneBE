package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import davidemancini.CapstoneBE.entities.Offerta;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyBadRequest;
import davidemancini.CapstoneBE.payloads.OffertaDTO;
import davidemancini.CapstoneBE.repositories.OffertaRepository;
import davidemancini.CapstoneBE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffertaService {
    @Autowired
    private OffertaRepository offertaRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AstaCalciatoreService astaCalciatoreService;

    public Offerta save (OffertaDTO body){
        if (body.offerente() == null || body.asta() == null) {
            throw new MyBadRequest("offerente o asta non possono essere null");
        }
        Utenti utente = utenteService.findById(body.offerente());
        AstaCalciatore astaCalciatore= astaCalciatoreService.findById(body.asta());
        Offerta nuovaOfferta = new Offerta(utente, body.valoreOfferta(), astaCalciatore);
      return offertaRepository.save(nuovaOfferta);
    }
}

package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import davidemancini.CapstoneBE.entities.Calciatori;
import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.AstaCalciatoreDTO;
import davidemancini.CapstoneBE.repositories.AstaCalciatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AstaCalciatoreService {
    @Autowired
    private AstaCalciatoreRepository astaCalciatoreRepository;

    @Autowired
    private CalciatoriService calciatoriService;

    @Autowired
    private SessioneAstaService sessioneAstaService;


    public AstaCalciatore save (AstaCalciatoreDTO body){
        Calciatori calciatore = calciatoriService.findById(body.calciatore());
        SessioneAsta sessioneAsta = sessioneAstaService.findById(body.asta());
        AstaCalciatore nuovaAstaCalciatore = new AstaCalciatore(calciatore,sessioneAsta);
       return astaCalciatoreRepository.save(nuovaAstaCalciatore);
    }

    public AstaCalciatore findById (UUID id){
       return astaCalciatoreRepository.findById(id).orElseThrow(()-> new MyNotFoundException("Asta con id "+ id+" non trovata"));
    }
    public AstaCalciatore updateOfferta(UUID astaId, Integer nuovaOfferta, String offerenteUsername) {
        AstaCalciatore asta = astaCalciatoreRepository.findById(astaId)
                .orElseThrow(() -> new RuntimeException("Asta non trovata"));

        asta.setOffertaAttuale(nuovaOfferta);
        asta.setOfferenteUsername(offerenteUsername);

        return astaCalciatoreRepository.save(asta);
    }
}

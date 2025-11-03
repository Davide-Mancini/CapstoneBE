package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.TipoUtente;
import davidemancini.CapstoneBE.payloads.TipoUtenteDTO;
import davidemancini.CapstoneBE.repositories.TipoUtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoUtenteService {
    @Autowired
    private TipoUtenteRepository tipoUtenteRepository;

    public TipoUtente save (TipoUtenteDTO body){
        TipoUtente newTipoUtente = new TipoUtente(body.tipo());
       return tipoUtenteRepository.save(newTipoUtente);

    }
}

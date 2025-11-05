package davidemancini.CapstoneBE.services;


import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//CREO QUESTO SERVICE PER POTER GESTIRE IN MODO PIU SICURO IL LOGOUT, QUANDO UN UTENTE EFFETTUA IL LOGOUT IL VECCHIO TOKEN VIENE INSERITO IN QUESTA LISTA E NON SARà PIU VALIDO
@Service
public class InvalidTokenList {

    //CREO UN MAP CON CHIAVE TOKEN E VALORE INSTANT(ORA)
    private Map<String, Instant> invalidList = new ConcurrentHashMap<>();


    //METODO PER INSERIRE TOKEN NELLA LISTA
    public void addToInvalidLits(String token, Instant dataScadenza){
        invalidList.put(token,dataScadenza);
    }

    public boolean isInvalidated(String token){
        if (token==null) {
            return false;
        }
        Instant scadenza = invalidList.get(token);
        if (scadenza==null){
            return false;
        }
        if (Instant.now().isAfter(scadenza)){
            invalidList.remove(token);
            return false;
        }
        return true;
    }
}

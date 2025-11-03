package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.Calciatori;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.CalciatoriDTO;
import davidemancini.CapstoneBE.repositories.CalciatoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CalciatoriService {
    @Autowired
    private CalciatoriRepository calciatoriRepository;

    //SALVA NUOVO GIOCATORE(PUO ESSERE UTILE IN UNA SPECIE DI BACK OFFICE)
    public Calciatori saveNewCalciatore(CalciatoriDTO body){
        Calciatori newCalciatore = new Calciatori(body.id(), body.cognome(), body.nomeCompleto(), body.ruolo(), body.valore(), body.squadra(), body.nazionalita());
        return calciatoriRepository.save(newCalciatore);
    }

    //CERCA TUTTI I CALCIATORI
    public Page<Calciatori> findAll(int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return calciatoriRepository.findAll(pageable);
    }

    //CERCA PER ID
    public Calciatori findById (long id){
        return calciatoriRepository.findById(id).orElseThrow(()-> new MyNotFoundException("Calciatore con id " + id+" non trovato"));
    }

    //CERCA PER NOME O PARTE DI ESSO
    public Page<Calciatori> findByCognomeContaining(String cognome, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return calciatoriRepository.findByCognomeContaining(cognome,pageable);
    }

    //AGGIORNA CALCIATORE
    public Calciatori updateCalciatore(long id, CalciatoriDTO body){
        Calciatori trovato = findById(id);
        trovato.setCognome(body.cognome());
        trovato.setNome_completo(body.nomeCompleto());
        trovato.setRuolo(body.ruolo());
        trovato.setValore(body.valore());
        trovato.setSquadra(body.squadra());
        trovato.setNazionalita(body.nazionalita());
        return calciatoriRepository.save(trovato);
    }

    //ELIMINA CALCIATORE
    public void deleteCalciatore(long id){
        Calciatori trovato = findById(id);
        calciatoriRepository.delete(trovato);
    }

}

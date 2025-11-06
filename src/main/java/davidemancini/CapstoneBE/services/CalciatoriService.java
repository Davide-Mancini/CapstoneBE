package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.Calciatori;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.CalciatoriDTO;
import davidemancini.CapstoneBE.repositories.CalciatoriRepository;
import davidemancini.CapstoneBE.specifications.CalciatoriSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CalciatoriService {
    @Autowired
    private CalciatoriRepository calciatoriRepository;



    //SALVA NUOVO GIOCATORE(PUO ESSERE UTILE IN UNA SPECIE DI BACK OFFICE)
    public Calciatori saveNewCalciatore(CalciatoriDTO body){
        Calciatori newCalciatore = new Calciatori(body.id(), body.cognome(), body.nomeCompleto(), body.ruolo(), body.valore(), body.squadra(), body.nazionalita(), body.campioncino());
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

    //CERCA PER RUOLO
    public Page<Calciatori> findByRuolo (String ruolo, int pageNumber, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return calciatoriRepository.findByRuolo(ruolo,pageable);
    }

    //MULTISEARCH
    //UTILIZZO IL FINDALL DELLA REPOSITORY DOPO AVERLA ESTESA ANCHE A JPASPECIFICATIONEXECUTOR, E ALL'INTERNO INSERISCO LA SPECIFICATION CON I VARI METODI A CUI PASSO I PARAMETRI
    public Page<Calciatori> multiSearch (String ruolo, String cognome, String squadra,Long valore,int pageNumber, int pageSize, String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return calciatoriRepository.findAll(Specification.anyOf(CalciatoriSpecification.findByRuolo(ruolo)).and(CalciatoriSpecification.findByNomeContaining(cognome))
                .and(CalciatoriSpecification.findBySquadra(squadra)).and(CalciatoriSpecification.findByValore(valore)),pageable);
    }

}

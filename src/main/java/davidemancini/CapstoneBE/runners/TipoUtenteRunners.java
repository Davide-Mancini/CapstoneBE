package davidemancini.CapstoneBE.runners;


import davidemancini.CapstoneBE.entities.TipoUtente;
import davidemancini.CapstoneBE.repositories.TipoUtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


//QUESTO RUNNER CREA DEI NUOVI OGGETTI DI TIPO TIPO UTENTE ALL'AVVIO DELL'APPLICAZIONE E LI SALVA NEL DB
@Component
public class TipoUtenteRunners implements CommandLineRunner {
    @Autowired
    private TipoUtenteRepository tipoUtenteRepository;

    @Override
    public void run(String... args) throws Exception {
    //CONTROLLO CHE NON ESISTANO GIA NEL DB

        if(tipoUtenteRepository.findByTipo("ADMIN").isEmpty()){
            tipoUtenteRepository.save(new TipoUtente("ADMIN"));
            System.out.println("Nuovo Admin salvato");
        }
        if (tipoUtenteRepository.findByTipo("USER").isEmpty()){
            tipoUtenteRepository.save(new TipoUtente("USER"));
            System.out.println("Nuovo User salvato");
        }
    }
}

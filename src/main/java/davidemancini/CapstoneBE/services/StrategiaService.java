package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.*;
import davidemancini.CapstoneBE.exceptions.MyNotFoundException;
import davidemancini.CapstoneBE.payloads.StrategiaDTO;
import davidemancini.CapstoneBE.repositories.CalciatoriRepository;
import davidemancini.CapstoneBE.repositories.DettaglioStrategiaRepository;
import davidemancini.CapstoneBE.repositories.StrategiaRepository;
import davidemancini.CapstoneBE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StrategiaService {
    @Autowired
    private StrategiaRepository strategiaRepository;
    @Autowired
    private CalciatoriRepository calciatoriRepository;
    @Autowired
    private DettaglioStrategiaRepository dettaglioStrategiaRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Strategia save(StrategiaDTO body, Utenti utenti) {
        Utenti trovato = utenteRepository.findById(body.utente()).orElseThrow(() -> new MyNotFoundException("Utente non trovato"));
        Strategia strategia = new Strategia();
        strategia.setNome(body.nome());
        strategia.setBudget(body.budgetTotale());
        strategia.setAppunti(body.appuntiGenerali());
        strategia.setUtenti(trovato);
        List<DettaglioStrategia> dettagli = body.dettagli().stream().map(dettaglioPayload -> {
            DettaglioStrategia dettaglioStrategia = new DettaglioStrategia();
            Calciatori calciatori = calciatoriRepository.findById(dettaglioPayload.calciatoreId()).orElseThrow(() -> new MyNotFoundException("Calciatore non trovato"));
            dettaglioStrategia.setCalciatori(calciatori);
            dettaglioStrategia.setPrezzoProposto(dettaglioPayload.prezzoProposto());
            dettaglioStrategia.setTipoGiocatore(TipoGiocatore.valueOf(dettaglioPayload.tipo().toUpperCase()));
            dettaglioStrategia.setAppuntiGiocatore(dettaglioPayload.appuntiGiocatore());
            dettaglioStrategia.setStrategia(strategia);

            if (body.budgetTotale() > 0) {
                double percentuale = ((double) dettaglioPayload.prezzoProposto() / body.budgetTotale()) * 100;
                dettaglioStrategia.setPercentuale(percentuale);
            } else {
                dettaglioStrategia.setPercentuale(0.0);
            }
            return dettaglioStrategia;
        }).toList();
        strategia.setDettagli(dettagli);
        return strategiaRepository.save(strategia);
    }

    public Strategia findById(UUID id) {
        return strategiaRepository.findById(id).orElseThrow(() -> new MyNotFoundException("strategia non trovata"));
    }

    public List<Strategia> findByUtente(Utenti utenti) {
        return strategiaRepository.findByUtenti(utenti);
    }
}

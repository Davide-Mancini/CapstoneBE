package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.*;
import davidemancini.CapstoneBE.payloads.AstaTerminataDTO;
import davidemancini.CapstoneBE.repositories.AstaCalciatoreRepository;
import davidemancini.CapstoneBE.repositories.CasellaRosaRepository;
import davidemancini.CapstoneBE.repositories.RosaUtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Map;

@Service
public class RosaUtenteService {

    @Autowired
    private RosaUtenteRepository rosaUtenteRepository;

    @Autowired
    private AstaCalciatoreRepository astaCalciatoreRepository;

    @Autowired
    private CasellaRosaRepository casellaRosaRepository;


    public RosaUtente creaRosaPerUtente(Utenti utente) {
        RosaUtente nuovaRosa = new RosaUtente(utente);

        System.out.println("Nuova rosa id " + nuovaRosa.getId());
        addCaselleByRuolo(nuovaRosa, "P", 3);
        addCaselleByRuolo(nuovaRosa, "D", 8);
        addCaselleByRuolo(nuovaRosa, "C", 8);
        addCaselleByRuolo(nuovaRosa, "A", 6);
        System.out.println("CASSELLE PRIMA DEL SALVATAGGIO: " + nuovaRosa.getCaselle().size());
        nuovaRosa = rosaUtenteRepository.save(nuovaRosa);
        System.out.println("CASSELLE DOPO SALVATAGGIO: " + nuovaRosa.getCaselle().size());
        return nuovaRosa;
    }

    private void addCaselleByRuolo(RosaUtente rosa, String ruolo, int count) {
        for (int i = 1; i <= count; i++) {
            CasellaRosa c = new CasellaRosa(ruolo, i);
            c.setRosa(rosa);
            rosa.addCasella(c);
        }

    }


    public CasellaRosa primaCasellaLibera(RosaUtente rosa, String ruolo) {
        return rosa.getCaselle().stream()
                .filter(casella -> casella.getRuolo().equals(ruolo))
                .filter(casella -> casella.getCalciatore() == null)
                .sorted(Comparator.comparing(CasellaRosa::getPosizione))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public AstaTerminataDTO terminaAsta(AstaCalciatore astaCalciatore) {
        Offerta vincitrice = astaCalciatore.getOfferte().stream().max(Comparator.comparing(Offerta::getValoreOfferta))
                .orElse(null);

        if (vincitrice == null) {
            astaCalciatore.setStatoAsta(StatoAsta.SENZA_OFFERTE);
            astaCalciatoreRepository.save(astaCalciatore);
            return new AstaTerminataDTO("Nessuna offerta", "Nessun vincitore", 0, "", 0);
        }
        Utenti vincitore = vincitrice.getUtente_offerente();
        Calciatori calciatore = astaCalciatore.getCalciatore();
        int prezzo = vincitrice.getValoreOfferta();
        RosaUtente rosa = rosaUtenteRepository.findByUtenteIdWithCaselle(vincitore.getId())
                .orElseThrow(() -> new RuntimeException("Rosa non trovata per utente " + vincitore.getId()));
        

        CasellaRosa casella = primaCasellaLibera(rosa, calciatore.getRuolo());
        if (casella != null) {
            casella.setCalciatore(calciatore);
            casella.setPrezzoAcquisto(prezzo);
            casella.setRosa(rosa);
            rosa.setCreditiResidui(rosa.getCreditiResidui() - prezzo);
            casellaRosaRepository.save(casella);
        }
        rosaUtenteRepository.save(rosa);
        astaCalciatore.setVincitore(vincitore);
        astaCalciatore.setOffertaFinale(prezzo);
        astaCalciatore.setStatoAsta(StatoAsta.CHIUSA);
        astaCalciatoreRepository.save(astaCalciatore);

        return new AstaTerminataDTO(
                calciatore.getCognome(),
                vincitore.getUsername(),
                prezzo,
                calciatore.getRuolo(),
                rosa.getCreditiResidui()

        );


    }
}

package davidemancini.CapstoneBE.services;

import davidemancini.CapstoneBE.entities.*;
import davidemancini.CapstoneBE.payloads.AstaTerminataDTO;
import davidemancini.CapstoneBE.repositories.AstaCalciatoreRepository;
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


    public RosaUtente creaRosaPerUtente(Utenti utente) {
        RosaUtente nuovaRosa = new RosaUtente(utente);
        for (int i = 1; i <= 3; i++) nuovaRosa.getCaselle().add(new CasellaRosa("P", i));
        for (int i = 1; i <= 8; i++) nuovaRosa.getCaselle().add(new CasellaRosa("D", i));
        for (int i = 1; i <= 8; i++) nuovaRosa.getCaselle().add(new CasellaRosa("C", i));
        for (int i = 1; i <= 6; i++) nuovaRosa.getCaselle().add(new CasellaRosa("A", i));
        return rosaUtenteRepository.save(nuovaRosa);
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

        }
        Utenti vincitore = vincitrice.getUtente_offerente();
        Calciatori calciatore = astaCalciatore.getCalciatore();
        int prezzo = vincitrice.getValoreOfferta();
        RosaUtente rosa = rosaUtenteRepository.findByUtenteIdWithCaselle(vincitore.getId())
                .orElseGet(() -> {
                    // Se per qualche motivo non esiste, creala al volo
                    System.out.println("Creazione rosa di emergenza per: " + vincitore.getUsername());
                    return creaRosaPerUtente(vincitore);
                });
        CasellaRosa casella = primaCasellaLibera(rosa, calciatore.getRuolo());
        if (casella != null) {
            casella.setCalciatore(calciatore);
            casella.setPrezzoAcquisto(prezzo);
            rosa.setCreditiResidui(rosa.getCreditiResidui() - prezzo);
        }
        astaCalciatore.setVincitore(vincitore);
        astaCalciatore.setOffertaFinale(prezzo);
        astaCalciatore.setStatoAsta(StatoAsta.CHIUSA);
        astaCalciatoreRepository.save(astaCalciatore);

        return new AstaTerminataDTO(
                calciatore.getNome_completo(),
                vincitore.getUsername(),
                prezzo,
                calciatore.getRuolo()
        );

    }
}

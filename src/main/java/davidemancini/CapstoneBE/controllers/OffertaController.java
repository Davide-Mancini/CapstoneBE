package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.*;
import davidemancini.CapstoneBE.payloads.*;
import davidemancini.CapstoneBE.services.AstaCalciatoreService;
import davidemancini.CapstoneBE.services.OffertaService;
import davidemancini.CapstoneBE.services.RosaUtenteService;
import davidemancini.CapstoneBE.services.SessioneAstaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
public class OffertaController {
    @Autowired
    private OffertaService offertaService;
    @Autowired
    private AstaCalciatoreService astaCalciatoreService;
    @Autowired
    private RosaUtenteService rosaUtenteService;
    @Autowired
    private SessioneAstaService sessioneAstaService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/inviaofferta/{idAsta}")
    @SendTo("/topic/public/{idAsta}")
    public OffertaResponseDTO sendOfferta(@DestinationVariable UUID idAsta, @Payload OffertaDTO body) {
        try {
            Offerta nueOfferta = offertaService.save(body);

//            AstaCalciatore astaCalciatore = astaCalciatoreService.findById(idAsta);
//            astaCalciatore.setOffertaAttuale(nueOfferta.getValoreOfferta());
//            astaCalciatore.setOfferenteUsername(nueOfferta.getUtente_offerente().getUsername());
//            astaCalciatoreService.save(astaCalciatore);

            AstaCalciatore astaAggiornata = astaCalciatoreService.updateOfferta(
                    body.asta(), nueOfferta.getValoreOfferta(), nueOfferta.getUtente_offerente().getUsername()
            );
            System.out.println("Offerta finale: " + nueOfferta);
            return new OffertaResponseDTO(
                    nueOfferta.getValoreOfferta(),
                    nueOfferta.getUtente_offerente().getUsername(),
                    body.asta(),
                    astaAggiornata.getDataInizio()
            );

        } catch (Exception e) {
            System.err.println("ERRORE in sendOfferta: " + e.getMessage());
            return null;
        }
    }

    @MessageMapping("/utente-entrato/{idAsta}")
    @SendTo("/topic/utente-entrato/{idAsta}")
    public SessioneAsta addUSer(@Payload AddUtenteDTO dto, @DestinationVariable UUID idAsta) {
        SessioneAsta aggiornata = sessioneAstaService.addUtente(idAsta, dto.id());

        return aggiornata;
    }
//    @MessageMapping("/utente-entrato/{idAsta}")
//    public void utenteEntrato(@Payload Utenti utente, @DestinationVariable UUID idAsta) {
//        messagingTemplate.convertAndSend("/topic/utente-entrato/" + idAsta, utente);
//    }


    @MessageMapping("/selezionaCalciatore/{idAsta}")
    @SendTo("/topic/calciatore-selezionato/{idAsta}")
    public CalciatoreSelezionatoDTO selezionaCalciatore(@DestinationVariable UUID idAsta, CalciatoreSelezionatoDTO calciatoreDto) {
        return calciatoreDto;
    }

    @MessageMapping("/iniziaAsta/{astaId}")
    @SendTo("/topic/asta-iniziata/{astaId}")
    public ResponseAstaCalciatoreDTO iniziaAsta(
            @RequestBody InizaAstaDTO request,
            @DestinationVariable UUID astaId) {
        if (!request.astaId().equals(astaId)) {
            throw new IllegalArgumentException("ID asta non valido");
        }

        AstaCalciatoreDTO dto = new AstaCalciatoreDTO(
                request.calciatoreId(),
                request.astaId()
        );

        AstaCalciatore nuovaAsta = astaCalciatoreService.save(dto);

        return new ResponseAstaCalciatoreDTO(
                nuovaAsta.getId(),
                nuovaAsta.getCalciatore().getNome_completo(),
                nuovaAsta.getCalciatore().getCampioncino(),
                nuovaAsta.getOffertaAttuale(),
                nuovaAsta.getDataInizio(),
                nuovaAsta.getDurataSecondi(),
                nuovaAsta.getStatoAsta()
        );
    }

    @MessageMapping("/termina-asta/{astaId}")
    @SendTo("/topic/asta-terminata/{astaId}")
    public AstaTerminataDTO terminaAsta(
            @DestinationVariable UUID astaId) {

        System.out.println("Richiesta chiusura asta: " + astaId);

        AstaCalciatore asta = astaCalciatoreService.findByIdWithOfferte(astaId);

        if (!asta.getStatoAsta().equals(StatoAsta.APERTA)) {
            return new AstaTerminataDTO("Asta già chiusa", "Sistema", 0, "", 0);
        }

        AstaTerminataDTO result = rosaUtenteService.terminaAsta(asta);
        System.out.println("RISULTATO TERMINA ASTA: " + result);
        return result;
    }
}

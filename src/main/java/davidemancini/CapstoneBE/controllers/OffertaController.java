package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import davidemancini.CapstoneBE.entities.Offerta;
import davidemancini.CapstoneBE.payloads.*;
import davidemancini.CapstoneBE.services.AstaCalciatoreService;
import davidemancini.CapstoneBE.services.OffertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class OffertaController {
    @Autowired
    private OffertaService offertaService;
    @Autowired
    private AstaCalciatoreService astaCalciatoreService;

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
                    body.asta()
            );

        } catch (Exception e) {
            System.err.println("ERRORE in sendOfferta: " + e.getMessage());
            return null;
        }
    }

    @MessageMapping("/aggiungiuser")
    @SendTo("/topic/public")
    public Offerta addUSer(@Payload Offerta offerta, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", offerta.getUtente_offerente());
        return offerta;
    }

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
                nuovaAsta.getOffertaAttuale()
        );
    }
}

package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Offerta;
import davidemancini.CapstoneBE.payloads.OffertaDTO;
import davidemancini.CapstoneBE.services.OffertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class OffertaController {
    @Autowired
    private OffertaService offertaService;

    @MessageMapping("/inviaofferta/{idAsta}")
    @SendTo("/topic/public/{idAsta}")
    public Offerta sendOfferta(@DestinationVariable UUID idAsta, @Payload OffertaDTO body){
        //TODO TORNARE UN NUOVO OGGETTO CON IL DTO E SALVARLO IN DB
        Offerta nueOfferta = offertaService.save(body);
        System.out.println("offerta "+nueOfferta);
        return nueOfferta;
    }
    @MessageMapping("/aggiungiuser")
    @SendTo("/topic/public")
    public Offerta addUSer(@Payload Offerta offerta, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",offerta.getUtente_offerente());
        return offerta;
    }


}

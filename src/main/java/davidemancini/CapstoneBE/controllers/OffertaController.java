package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Offerta;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class OffertaController {

    @MessageMapping("/inviaofferta")
    @SendTo("/topic/public")
    public Offerta sendOfferta(@Payload Offerta offerta){
        System.out.println("offerta "+offerta);
        return offerta;
    }

    @MessageMapping("/aggiungiuser")
    @SendTo("/topic/public")
    public Offerta addUSer(@Payload Offerta offerta, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",offerta.getOfferente());
        return offerta;
    }


}

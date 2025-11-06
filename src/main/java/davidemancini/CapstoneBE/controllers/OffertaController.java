package davidemancini.CapstoneBE.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OffertaController {

    @MessageMapping("/offerta")
    @SendTo("/topic/offerta")
    public String sendOfferta(String offerta){
        System.out.println("offerta"+offerta);
        return offerta;
    }
}

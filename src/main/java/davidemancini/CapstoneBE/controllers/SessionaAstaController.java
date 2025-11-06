package davidemancini.CapstoneBE.controllers;


import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.payloads.SessioneAstaDTO;
import davidemancini.CapstoneBE.services.SessioneAstaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessioniAsta")
public class SessionaAstaController {
    @Autowired
    private SessioneAstaService sessioneAstaService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public SessioneAsta create(@RequestBody SessioneAstaDTO body){
        SessioneAsta nuovaSessione = sessioneAstaService.create(body);
        messagingTemplate.convertAndSend("/topic/aste",nuovaSessione);
        return nuovaSessione;
    }
}

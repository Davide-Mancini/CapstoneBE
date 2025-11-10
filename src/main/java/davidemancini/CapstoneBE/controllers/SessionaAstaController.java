package davidemancini.CapstoneBE.controllers;


import davidemancini.CapstoneBE.entities.SessioneAsta;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.payloads.SessioneAstaDTO;
import davidemancini.CapstoneBE.services.SessioneAstaService;
import davidemancini.CapstoneBE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PatchMapping("/aggiungi-utente")
    public SessioneAsta addUtente (@RequestParam UUID idAsta, @RequestParam UUID idUtente){
        SessioneAsta astaAggiornata= sessioneAstaService.addUtente(idAsta,idUtente);
        return astaAggiornata;

    }

    @GetMapping("/{id}")
    public SessioneAsta getAstaById (@PathVariable UUID id){
        System.out.println("/sessioniAsta/"+id);
        return sessioneAstaService.findById(id);
    }
}

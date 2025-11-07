package davidemancini.CapstoneBE.config;

import davidemancini.CapstoneBE.entities.Offerta;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;
    @EventListener
    public void handleWebSocketDisconection(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username!= null){
            System.out.println("utente disconesso " + username);
            String messaggio = String.valueOf(Offerta.builder().offerente(username).build());
            messageSendingOperations.convertAndSend("/topic/public",messaggio);
        }
    }
    //TODO --> subsscribe event listener
}

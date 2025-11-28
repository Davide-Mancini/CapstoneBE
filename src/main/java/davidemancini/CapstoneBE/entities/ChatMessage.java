package davidemancini.CapstoneBE.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatMessage {
    private String nickname;
    private String contenuto;
    private LocalDateTime oraMessaggio;
    private String immagine;

    public ChatMessage(String nickname, String contenuto, LocalDateTime oraMessaggio, String immagine) {
        this.nickname = nickname;
        this.contenuto = contenuto;
        this.oraMessaggio = oraMessaggio;
        this.immagine = immagine;
    }
}

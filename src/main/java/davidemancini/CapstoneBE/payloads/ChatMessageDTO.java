package davidemancini.CapstoneBE.payloads;

import java.time.LocalDateTime;
import java.util.Date;

public record ChatMessageDTO(
        String nickname,
        String content,
        LocalDateTime oraMessaggio,
        String immagine
) {
}

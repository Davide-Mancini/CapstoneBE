package davidemancini.CapstoneBE.payloads.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsListDTO(
        String messaggio,
        LocalDateTime oraErrore,
        List<String> errori
) {
}

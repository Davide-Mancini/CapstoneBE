package davidemancini.CapstoneBE.payloads.errors;

import java.time.LocalDateTime;

public record ErrorsDTO(
        String messaggio,
        LocalDateTime data
) {
}

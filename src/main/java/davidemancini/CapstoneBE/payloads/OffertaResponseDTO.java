package davidemancini.CapstoneBE.payloads;

import java.time.LocalDateTime;
import java.util.UUID;

public record OffertaResponseDTO(
        int valoreOfferta,
        String offerente,
        UUID asta,
        LocalDateTime inizioAsta
) {
}

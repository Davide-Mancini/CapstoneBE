package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record OffertaResponseDTO(
        int valoreOfferta,
        String offerente,
        UUID asta
) {
}

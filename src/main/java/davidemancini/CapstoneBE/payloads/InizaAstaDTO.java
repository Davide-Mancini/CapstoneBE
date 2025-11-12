package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record InizaAstaDTO(
        long calciatoreId,
        UUID astaId
) {
}

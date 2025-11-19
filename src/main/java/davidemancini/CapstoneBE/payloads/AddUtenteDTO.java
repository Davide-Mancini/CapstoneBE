package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record AddUtenteDTO(UUID id,
                           String username) {
}

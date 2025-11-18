package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.CasellaRosa;

import java.util.List;
import java.util.UUID;

public record RosaUtenteDTO(UUID id,
                            int creditiResidui,
                            List<CasellaRosaDTO> caselle
) {
}

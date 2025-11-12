package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record ResponseAstaCalciatoreDTO(UUID id,
                                        String nomeCalciatore,
                                        String immagineUrl,
                                        int valoreBase) {
}

package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.StatoAsta;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseAstaCalciatoreDTO(UUID id,
                                        String nomeCalciatore,
                                        String immagineUrl,
                                        int valoreBase,
                                        LocalDateTime dataInizio,
                                        int duarataSecondi,
                                        StatoAsta statoAsta) {
}

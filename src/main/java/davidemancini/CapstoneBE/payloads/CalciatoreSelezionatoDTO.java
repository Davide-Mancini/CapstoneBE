package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record CalciatoreSelezionatoDTO(
        long id,
        String nomeCompleto,
        String immagineUrl,
        int valoreBase
) {
}

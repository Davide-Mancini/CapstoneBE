package davidemancini.CapstoneBE.payloads;

import java.util.List;
import java.util.UUID;

public record StrategiaDTO(
        String nome,
        int budgetTotale,
        String appuntiGenerali,
        List<DettaglioStrategiaDTO> dettagli,
        UUID utente
) {
}

package davidemancini.CapstoneBE.payloads;

import java.util.UUID;

public record DettaglioStrategiaDTO(
        long calciatoreId,
        int prezzoProposto,
        String tipo,
        String appuntiGiocatore
) {
}

package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import davidemancini.CapstoneBE.entities.Utenti;

import java.util.UUID;

public record OffertaDTO(int valoreOfferta,
                         UUID offerente,
                         UUID asta) {
}

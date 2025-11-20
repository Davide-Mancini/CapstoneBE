package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.SessioneAsta;

import java.util.List;
import java.util.UUID;

public record UtentiDTOPesante(
        UUID id,
        String nome,
        String cognome,
        String email,
        String username,
        String avatar,
        List<SessioniAstaDTOLeggero> sessioni
) {
}

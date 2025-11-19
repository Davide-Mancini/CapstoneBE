package davidemancini.CapstoneBE.entities;

import davidemancini.CapstoneBE.payloads.SessioniAstaDTOLeggero;
import davidemancini.CapstoneBE.payloads.UtentiDTOPesante;
import org.springframework.stereotype.Component;

@Component
public class UtentiMapper {
    public UtentiDTOPesante toDTOPesante(Utenti u) {
        return new UtentiDTOPesante(
                u.getId(),
                u.getNome(),
                u.getCognome(),
                u.getEmail(),
                u.getUsername(),
                u.getAvatar(),
                u.getSessioni().stream()
                        .map(this::toSessioneLeggera)
                        .toList()
        );
    }

    public SessioniAstaDTOLeggero toSessioneLeggera(SessioneAsta s) {
        return new SessioniAstaDTOLeggero(
                s.getId(),
                s.getNome_asta()

        );
    }
}

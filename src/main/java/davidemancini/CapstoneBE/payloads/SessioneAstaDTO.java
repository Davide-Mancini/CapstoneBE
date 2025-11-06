package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.StatoAsta;

public record SessioneAstaDTO(String nomeAsta,
                              int numPartecipanti,
                              int crediti) {
}

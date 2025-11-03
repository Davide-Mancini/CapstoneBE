package davidemancini.CapstoneBE.payloads;

public record CalciatoriDTO(long id,
                            String cognome,
                            String nomeCompleto,
                            String ruolo,
                            int valore,
                            String squadra,
                            String nazionalita ) {
}

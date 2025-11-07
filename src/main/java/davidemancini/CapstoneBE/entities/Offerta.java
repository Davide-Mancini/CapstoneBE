package davidemancini.CapstoneBE.entities;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offerta {
    private int valoreOfferta;
    private String offerente;

    public Offerta(String offerente, int valoreOfferta) {
        this.offerente = offerente;
        this.valoreOfferta = valoreOfferta;
    }
}

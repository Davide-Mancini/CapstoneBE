package davidemancini.CapstoneBE.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Offerta {
    @Id
    @GeneratedValue
    private UUID id;
    private int valoreOfferta;
    @ManyToOne
    private Utenti utente_offerente;
    @ManyToOne
    @JoinColumn(name = "asta_calciatore_id")
    private AstaCalciatore asta_calciatore;

    public Offerta(Utenti offerente, int valoreOfferta, AstaCalciatore astaCalciatore) {
        this.utente_offerente = offerente;
        this.valoreOfferta = valoreOfferta;
        this.asta_calciatore = astaCalciatore;
    }

    //TO STRING PERSONALIZZATO PER EVITARE LAZILY ERROR

    @Override
    public String toString() {
        return "Offerta{" +
                "valoreOfferta=" + valoreOfferta +
                ", utente_offerente=" + (utente_offerente!= null? utente_offerente.getId():null) +
                ", asta_calciatore=" + asta_calciatore +
                '}';
    }
}

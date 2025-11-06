package davidemancini.CapstoneBE.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.UUID;

@Entity
public class AstaCalciatore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private StatoAsta statoAsta;
    private int offerta_corrente;

    @ManyToOne
    private SessioneAsta sessioneAsta;

    @ManyToOne
    private Utenti vincitore;

    @ManyToOne
    private Calciatori calciatore;

    public AstaCalciatore(Calciatori calciatore, SessioneAsta sessioneAsta) {
        this.calciatore = calciatore;
        this.sessioneAsta = sessioneAsta;
        this.offerta_corrente = 1;
        this.statoAsta= StatoAsta.APERTA;

    }
}

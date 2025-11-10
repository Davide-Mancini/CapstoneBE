package davidemancini.CapstoneBE.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AstaCalciatore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private StatoAsta statoAsta;

    @ManyToOne
    private SessioneAsta sessioneAsta;

    @ManyToOne
    private Utenti vincitore;

    @OneToOne
    private Calciatori calciatore;

    @OneToMany(mappedBy = "asta_calciatore")
    private List<Offerta>offerte;

    private int valoreIniziale;
    private int offertaFinale;


    public AstaCalciatore(Calciatori calciatore, SessioneAsta sessioneAsta) {
        this.calciatore = calciatore;
        this.sessioneAsta = sessioneAsta;
        this.statoAsta= StatoAsta.APERTA;
        this.valoreIniziale=1;

    }
}

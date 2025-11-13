package davidemancini.CapstoneBE.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private List<Offerta> offerte;

    private int offertaAttuale = 1;
    private int offertaFinale;
    private String offerenteUsername;
    private LocalDateTime dataInizio = LocalDateTime.now();
    private int durataSecondi = 10;


    public AstaCalciatore(Calciatori calciatore, SessioneAsta sessioneAsta) {
        this.calciatore = calciatore;
        this.sessioneAsta = sessioneAsta;
        this.statoAsta = StatoAsta.APERTA;
    }

    @Override
    public String toString() {
        return "AstaCalciatore{" +
                "offertaAttuale=" + offertaAttuale +
                ", offertaFinale=" + offertaFinale +
                ", offerenteUsername='" + offerenteUsername + '\'' +
                ", calciatore=" + calciatore +
                ", sessioneAsta=" + sessioneAsta +
                ", statoAsta=" + statoAsta +
                '}';
    }
}

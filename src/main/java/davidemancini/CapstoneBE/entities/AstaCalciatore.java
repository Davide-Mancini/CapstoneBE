package davidemancini.CapstoneBE.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "asta_calciatore", uniqueConstraints = {
        @UniqueConstraint(name = "unique_calciatore_per_sessione", columnNames = {"calciatore_id", "sessione_asta_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class AstaCalciatore {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private StatoAsta statoAsta;

    @ManyToOne
    private SessioneAsta sessioneAsta;

    @ManyToOne
    private Utenti vincitore;

    @ManyToOne
    @JoinColumn(name = "calciatore_id")
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

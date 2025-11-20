package davidemancini.CapstoneBE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rose_utente")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RosaUtente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utenti_id")
    @JsonIgnore
    private Utenti utenti;

    private int creditiResidui;

    @OneToMany(mappedBy = "rosa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CasellaRosa> caselle = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sessione_asta_id")
    private SessioneAsta sessioneAsta;

    public RosaUtente(Utenti utenti) {
        this.utenti = utenti;
    }

    public void addCasella(CasellaRosa casella) {
        casella.setRosa(this);
        this.caselle.add(casella);
    }

}

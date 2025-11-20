package davidemancini.CapstoneBE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "caselle_rosa")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CasellaRosa {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "rosa_id")
    private RosaUtente rosa;

    @ManyToOne
    private Calciatori calciatore;

    private int prezzoAcquisto;

    private String ruolo;

    private int posizione;

    public CasellaRosa(String ruolo, int posizione, RosaUtente rosa) {
        this.ruolo = ruolo;
        this.posizione = posizione;
        this.rosa = rosa;
    }
}

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

    @OneToOne
    @JsonIgnore
    private Utenti utenti;

    private int creditiResidui;

    @OneToMany(mappedBy = "rosa")
    @JsonIgnore
    private List<CasellaRosa> caselle = new ArrayList<>();

    public RosaUtente(Utenti utenti) {
        this.utenti = utenti;
    }
}

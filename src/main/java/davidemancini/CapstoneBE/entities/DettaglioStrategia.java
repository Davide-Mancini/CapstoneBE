package davidemancini.CapstoneBE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DettaglioStrategia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int prezzoProposto;
    private double percentuale;
    private String appuntiGiocatore;
    @Enumerated(EnumType.STRING)
    private TipoGiocatore tipoGiocatore;
    @ManyToOne
    @JoinColumn(name = "strategia_id")
    @JsonIgnore
    private Strategia strategia;
    @ManyToOne
    @JoinColumn
    private Calciatori calciatori;

}

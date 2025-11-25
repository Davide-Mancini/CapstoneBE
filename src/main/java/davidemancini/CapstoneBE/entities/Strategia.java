package davidemancini.CapstoneBE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Strategia {
    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private int budget;
    private String appunti;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utenti utenti;
    @OneToMany(mappedBy = "strategia", cascade = CascadeType.ALL)
    
    private List<DettaglioStrategia> dettagli;


}

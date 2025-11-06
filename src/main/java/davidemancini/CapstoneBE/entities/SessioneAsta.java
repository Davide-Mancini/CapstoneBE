package davidemancini.CapstoneBE.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SessioneAsta {

    //ATTRIBUTI
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome_asta;
    @Enumerated(EnumType.STRING)
    private StatoAsta statoAsta;
    private LocalDateTime data_creazione;
    private int num_partecipanti;
    private int crediti; //DA RIVEDERE

    @ManyToMany
    @JoinTable(name = "sessione_utente")
    private List<Utenti> utenti= new ArrayList<>();

    @OneToMany(mappedBy = "sessioneAsta")
    private List<AstaCalciatore> astaCalciatore=new ArrayList<>();

    //COSTRUTTORE

    public SessioneAsta(String nome_asta, int num_partecipanti, int crediti) {
        this.nome_asta = nome_asta;
        this.statoAsta = StatoAsta.APERTA;
        this.data_creazione = LocalDateTime.now();
        this.num_partecipanti = num_partecipanti;
        this.crediti = crediti;
    }

    public SessioneAsta(String nome_asta, StatoAsta statoAsta) {
        this.nome_asta = nome_asta;
        this.statoAsta = statoAsta;
        this.data_creazione = LocalDateTime.now();
    }
}

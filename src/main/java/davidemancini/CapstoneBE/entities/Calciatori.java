package davidemancini.CapstoneBE.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Calciatori {

    //ATTRIBUTI
    @Id
    @Setter(AccessLevel.NONE)
    private long id;
    private String cognome;
    private String nome_completo;
    private String ruolo;
    private int valore;
    private String squadra;
    private String nazionalita;
    private String campioncino;




    //COSTRUTTORE
    public Calciatori(long id,String cognome, String nome_completo, String ruolo, int valore, String squadra, String nazionalita,String campioncino) {
        this.id=id;
        this.cognome = cognome;
        this.nome_completo = nome_completo;
        this.ruolo = ruolo;
        this.valore = valore;
        this.squadra = squadra;
        this.nazionalita = nazionalita;
        this.campioncino=campioncino;
    }
}

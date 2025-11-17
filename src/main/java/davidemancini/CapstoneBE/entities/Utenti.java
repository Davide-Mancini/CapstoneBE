package davidemancini.CapstoneBE.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password"})

public class Utenti implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;
    private String avatar;
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoUtente tipo;

    @ManyToMany(mappedBy = "utenti")
    @JsonIgnore
    private List<SessioneAsta> sessioni = new ArrayList<>();

    @OneToOne(mappedBy = "utenti")
   
    private RosaUtente rosa;

    //COSTRUTTORE


    public Utenti(String nome, String cognome, String email, String password, String username, TipoUtente tipo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome; //AVATAR CON INIZIALI
        this.username = username;
        this.tipo = tipo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipo.getTipo()));
    }

    @Override
    public String toString() {
        return "Utenti{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}

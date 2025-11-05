package davidemancini.CapstoneBE.security;

import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyUnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String segreto;


    // QUESTO METODO GENERA IL TOKEN. QUI DEFINISCO LA DATA DI EMISSIONE, LA DATA DI SCADENZA, L'ID DELL'UTENTE E LA FIRMA TRAMITE IL SEGRETO SALVBATO NELL'ENV
    public String cretaeToken(Utenti utente){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(segreto.getBytes()))
                .compact();
    }

    //METODO DI VERIFICA DEDL TOKEN
    public void verificaToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(segreto.getBytes())).build().parse(token);
        }catch (Exception ex){
            throw new MyUnauthorizedException("Errori nel Token");
        }
    }

    public Instant getScadenzaFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(segreto.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().toInstant();
    }

    //METODO PER RECUPERARE ID DAL TOKEN PER USARE LO /ME
    public UUID idFromToken(String token){
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(segreto.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}

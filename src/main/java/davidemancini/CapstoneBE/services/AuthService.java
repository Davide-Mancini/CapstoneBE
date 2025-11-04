package davidemancini.CapstoneBE.services;


import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyUnauthorizedException;
import davidemancini.CapstoneBE.payloads.LoginDTO;
import davidemancini.CapstoneBE.security.JWTTools;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;

    //QUESTO METODO FA UN CONTROLLO SULLA PASSWORD E SE TUTTO OK, GENERA UN TOKEN
    public String checkCredentialsAndGenerateToken(LoginDTO body){
        Utenti trovato = utenteService.findByEmail(body.email());
        if (bCrypt.matches(body.password(),trovato.getPassword())){
        return jwtTools.cretaeToken(trovato);
        }else {
            throw new MyUnauthorizedException("Credenziali errate!"); //IN FASE DI LOGIN NON DARE MAI ERRORI SPEICFICI SU DOVE SIA L'ERRORE(ES:PASSWORD ERRATA) PER QUESTIONI DI SICUREZZA
        }
    }

    //CON QUESTO METODO RECUPERO L'UTENTE LOGGATO GRAZIE AL TOKEN SALVATO NEI COOKIE
    public Utenti getUserFromCooki(HttpServletRequest request){
        String token = null;
        if (request.getCookies()!=null){
            for (Cookie cookie: request.getCookies()){
                if("jwt".equals(cookie.getName())){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if (token==null){
            throw new MyUnauthorizedException("Token mancante");
        }
        jwtTools.verificaToken(token);
        UUID userId = jwtTools.idFromToken(token);
        return utenteService.findById(userId);
    }

    //LOGOUT
    public void logout(){

    }
}

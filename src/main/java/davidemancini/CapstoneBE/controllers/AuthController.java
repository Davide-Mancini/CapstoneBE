package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyValidationException;
import davidemancini.CapstoneBE.payloads.LoginDTO;
import davidemancini.CapstoneBE.payloads.LoginResponseDTO;
import davidemancini.CapstoneBE.payloads.UtentiDTO;
import davidemancini.CapstoneBE.services.AuthService;
import davidemancini.CapstoneBE.services.UtenteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login (@RequestBody LoginDTO body, HttpServletResponse response){
        String token = authService.checkCredentialsAndGenerateToken(body);
        //SALVO IL TOKEN IN UN COOKIE COSI DA POTER RIMANERE LOGGATO ANCHE SE RIAGGIORNO LA PAGINA. MEGLIO RISPETTO AL SALVATAGGIO IN LOCAL STORAGE
        Cookie cookie = new Cookie("jwt",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        response.addHeader("Set-Cookie",
                String.format("jwt=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Lax", token, 24 * 60 * 60)
        );
        response.addCookie(cookie);
        return new LoginResponseDTO(token);
    }

    @PostMapping("/registrazione")
    @ResponseStatus(HttpStatus.CREATED)
    public Utenti registrazione (@RequestBody @Validated UtentiDTO body, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new MyValidationException(bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return utenteService.save(body);
    }

    @GetMapping("/me")
    public Utenti utenteLoggato (HttpServletRequest request){
        return authService.getUserFromCooki(request);
    }
}

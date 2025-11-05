package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UtentiController {
    @Autowired
    private UtenteService utenteService;

    @PatchMapping("/me/avatar")
    public Utenti uploadImg(@AuthenticationPrincipal Utenti loggato, @RequestParam("avatar")MultipartFile file){
        return utenteService.uploadAvatar(loggato,file);
    }
}

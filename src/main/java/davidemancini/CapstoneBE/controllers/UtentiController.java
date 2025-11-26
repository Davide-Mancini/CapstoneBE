package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.payloads.UtentiDTO;
import davidemancini.CapstoneBE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UtentiController {
    @Autowired
    private UtenteService utenteService;

    @PatchMapping("/me/avatar")
    public Utenti uploadImg(@AuthenticationPrincipal Utenti loggato, @RequestParam("avatar") MultipartFile file) {
        return utenteService.uploadAvatar(loggato, file);
    }

    @PutMapping("/{id}")
    public Utenti update(@PathVariable UUID id, @RequestBody UtentiDTO body) {
        return utenteService.findByIdAndUpdate(id, body);
    }

}
package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.Strategia;
import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.payloads.StrategiaDTO;
import davidemancini.CapstoneBE.services.StrategiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/strategie")
public class StrategiaController {
    @Autowired
    private StrategiaService strategiaService;

//    @GetMapping
//    public List<Strategia> getStrategieUtente(@AuthenticationPrincipal Utenti utenti) {
//    return strategiaService.
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Strategia create(@RequestBody StrategiaDTO body, @AuthenticationPrincipal Utenti utenti) {
        return strategiaService.save(body, utenti);
    }

    @GetMapping("/{id}")
    public Strategia findById(@PathVariable UUID id) {
        return strategiaService.findById(id);
    }

    @GetMapping
    public List<Strategia> getStrategieUtente(@AuthenticationPrincipal Utenti utente) {
        return strategiaService.findByUtente(utente);
    }

}

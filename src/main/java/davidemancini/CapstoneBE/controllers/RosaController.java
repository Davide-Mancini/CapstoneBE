package davidemancini.CapstoneBE.controllers;

import davidemancini.CapstoneBE.entities.CasellaRosa;
import davidemancini.CapstoneBE.entities.RosaUtente;
import davidemancini.CapstoneBE.payloads.CasellaRosaDTO;
import davidemancini.CapstoneBE.payloads.RosaUtenteDTO;
import davidemancini.CapstoneBE.repositories.RosaUtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rosa")
public class RosaController {

    @Autowired
    private RosaUtenteRepository rosaUtenteRepository;

    @GetMapping("/utente/{utenteId}/sessione/{sessioneId}")
    public ResponseEntity<RosaUtenteDTO> getRosaUtente(@PathVariable UUID utenteId, @PathVariable UUID sessioneId) {
        RosaUtente rosa = rosaUtenteRepository.findByUtenteIdAndSessioneAstaIdWithCaselle(utenteId, sessioneId)
                .orElseThrow(() -> new RuntimeException("Rosa non trovata"));

        return ResponseEntity.ok(convertiInDTO(rosa));
    }

    private RosaUtenteDTO convertiInDTO(RosaUtente rosa) {
        RosaUtenteDTO dto = new RosaUtenteDTO(rosa.getId(), rosa.getCreditiResidui(), rosa.getCaselle().stream()
                .map(this::convertiCasellaInDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private CasellaRosaDTO convertiCasellaInDTO(CasellaRosa casella) {
        String nomeCalciatore = casella.getCalciatore() != null
                ? casella.getCalciatore().getCognome()
                : null;

        return new CasellaRosaDTO(
                casella.getRuolo(),
                casella.getPosizione(),
                nomeCalciatore,
                casella.getPrezzoAcquisto()
        );
    }

}

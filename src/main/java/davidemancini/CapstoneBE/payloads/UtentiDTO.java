package davidemancini.CapstoneBE.payloads;

import davidemancini.CapstoneBE.entities.TipoUtente;
import jakarta.validation.constraints.*;

public record UtentiDTO(
        @NotBlank(message = "Nome obbligatorio!")
        @Size(min = 2, max = 30, message = "Il nome deve avere minimo 2 caratteri e massimo 30")
        String nome,
        @NotBlank(message = "Cognome obbligatorio!")
        @Size(min = 2, max = 30, message = "Il cognome deve avere minimo 2 caratteri e massimo 30")
        String cognome,
        @NotBlank(message = "Email obbligatoria!")
        @Email(message = "Inserisci l'email nel formato corretto")
        String email,
        @NotBlank(message = "Password obbligatoria!")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$\n", message = "La password deve avere: •Almeno una lettera maiuscola •Almeno una lettera minuscola  •Lunghezza di almeno 6 caratteri")
        String password,
        @NotBlank(message = "Username obbligatorio!")
        @Size(min = 3, max = 20, message = "L'username deve avere minimo 3 caratteri e massimo 20")
        String username,
        String tipoUtente

                        ) {
}

package davidemancini.CapstoneBE.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Email obbligatoria")
        @Email(message = "Inserire l'email nel formato corretto")
        String email,
        @NotBlank
        String password) {
}

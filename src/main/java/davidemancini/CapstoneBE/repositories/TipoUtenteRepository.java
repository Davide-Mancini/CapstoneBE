package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.TipoUtente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipoUtenteRepository extends JpaRepository<TipoUtente, UUID> {
    Optional<TipoUtente> findByTipo(String tipo);
}

package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utenti, UUID> {
}

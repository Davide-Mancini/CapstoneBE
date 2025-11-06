package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.SessioneAsta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessioneAstaRepository extends JpaRepository<SessioneAsta, UUID> {
}

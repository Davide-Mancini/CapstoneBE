package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.RosaUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RosaUtenteRepository extends JpaRepository<RosaUtente, UUID> {
    @Query("SELECT r FROM RosaUtente r LEFT JOIN FETCH r.caselle WHERE r.utenti.id = :utenteId")
    Optional<RosaUtente> findByUtenteIdWithCaselle(@Param("utenteId") UUID utenteId);
}

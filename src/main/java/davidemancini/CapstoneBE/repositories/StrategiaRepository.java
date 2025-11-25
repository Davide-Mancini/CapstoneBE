package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.Strategia;
import davidemancini.CapstoneBE.entities.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StrategiaRepository extends JpaRepository<Strategia, UUID> {
    List<Strategia> findByUtenti(Utenti utenti);
}

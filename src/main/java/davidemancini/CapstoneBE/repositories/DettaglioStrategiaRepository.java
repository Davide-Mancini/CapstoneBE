package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.DettaglioStrategia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DettaglioStrategiaRepository extends JpaRepository<DettaglioStrategia, UUID> {
}

package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.AstaCalciatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AstaCalciatoreRepository extends JpaRepository<AstaCalciatore, UUID> {
}

package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.CasellaRosa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Repository
public interface CasellaRosaRepository extends JpaRepository<CasellaRosa, UUID> {
}

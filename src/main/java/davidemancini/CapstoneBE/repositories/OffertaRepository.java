package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OffertaRepository extends JpaRepository<Offerta, UUID> {
}

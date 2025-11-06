package davidemancini.CapstoneBE.repositories;

import davidemancini.CapstoneBE.entities.Calciatori;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CalciatoriRepository extends JpaRepository<Calciatori,Long>, JpaSpecificationExecutor<Calciatori> {
    Page<Calciatori> findByCognomeContaining(String cognome, Pageable pageable);
    Page<Calciatori> findByRuolo(String ruolo,Pageable pageable);
}

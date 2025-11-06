package davidemancini.CapstoneBE.specifications;

import davidemancini.CapstoneBE.entities.Calciatori;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

//SPECIFICATION UN MODO PER CREARE QUERY CONCATENATE E COMPLESSE
//CREO TUTTI I METODI PER CUI VOGLIO FILTRARE I CALCIATORI E LI USERO NEL SERVICE
public class CalciatoriSpecification {

    public static Specification<Calciatori> findByRuolo(String ruolo){
        return ((root, query, criteriaBuilder) -> (
                ruolo==null||ruolo.isBlank()?null:criteriaBuilder.equal(criteriaBuilder.lower(root.get("ruolo")),ruolo)
                ));
    }
    public static Specification<Calciatori> findByNomeContaining(String cognome){
        return ((root, query, criteriaBuilder) -> (
                cognome==null||cognome.isBlank()?null:criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")),"%"+cognome.toLowerCase()+"%")
                ));
    }
    public static Specification<Calciatori> findBySquadra(String squadra){
        return ((root, query, criteriaBuilder) -> (
                squadra==null||squadra.isBlank()?null:criteriaBuilder.equal(criteriaBuilder.lower(root.get("squadra")),squadra.toLowerCase())
                ));
    }

    public static Specification<Calciatori> findByValore(Long valore){
        return ((root, query, criteriaBuilder) -> (
                valore == null?null:criteriaBuilder.lessThanOrEqualTo(root.get("valore"),valore)
                ));
    }
}

package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.Language;

public interface LanguageRepository extends JpaRepository <Language,Integer>{
    boolean existsByName(String name);
}

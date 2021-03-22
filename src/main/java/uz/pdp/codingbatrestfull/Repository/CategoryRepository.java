package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);
}

package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.Example;

public interface ExampleRepository extends JpaRepository<Example,Integer> {
}

package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    boolean existsByName(String name);
}

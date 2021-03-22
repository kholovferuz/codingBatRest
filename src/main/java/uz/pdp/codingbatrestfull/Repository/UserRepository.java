package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
}

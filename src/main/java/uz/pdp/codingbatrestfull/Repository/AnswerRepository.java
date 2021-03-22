package uz.pdp.codingbatrestfull.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatrestfull.Entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}

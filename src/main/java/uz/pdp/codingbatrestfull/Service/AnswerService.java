package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.AnswerDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Entity.Answer;
import uz.pdp.codingbatrestfull.Entity.Task;
import uz.pdp.codingbatrestfull.Repository.AnswerRepository;
import uz.pdp.codingbatrestfull.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    TaskRepository taskRepository;

    // READ all answers
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    // READ answer by id
    public Answer getOneAnswer(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.orElse(null);
    }

    // CREATE new answer
    public Result addAnswer(AnswerDTO answerDTO) {

        // check task
        Optional<Task> optionalTask = taskRepository.findById(answerDTO.getTaskId());
        if (optionalTask.isEmpty()) {
            return new Result("Task with this id is not found", false);
        }
        Answer newAnswer = new Answer();
        newAnswer.setIsCorrect(answerDTO.getIsCorrect());
        newAnswer.setTask(optionalTask.get());


        answerRepository.save(newAnswer);
        return new Result("Answer added", true);

    }

    // UPDATE answer by id
    public Result updateAnswer(Integer id, AnswerDTO answerDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            // check task
            Optional<Task> optionalTask = taskRepository.findById(answerDTO.getTaskId());
            if (optionalTask.isEmpty()) {
                return new Result("Task with this id is not found", false);
            }
            Answer editedAnswer = optionalAnswer.get();
            editedAnswer.setIsCorrect(answerDTO.getIsCorrect());
            editedAnswer.setTask(optionalTask.get());


            answerRepository.save(editedAnswer);
            return new Result("Answer updated", true);
        }
        return new Result("Answer with this id is not found", false);
    }

    // DELETE answer by id
    public Result deleteAnswerById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            answerRepository.deleteById(id);
            return new Result("Answer deleted", true);
        }
        return new Result("Answer with this id is not found", false);
    }
}

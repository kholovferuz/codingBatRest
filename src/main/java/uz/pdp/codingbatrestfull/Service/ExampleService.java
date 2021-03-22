package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.ExampleDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Entity.Example;
import uz.pdp.codingbatrestfull.Entity.Task;
import uz.pdp.codingbatrestfull.Repository.ExampleRepository;
import uz.pdp.codingbatrestfull.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    // READ all examples
    public List<Example> getAllExamples() {
        return exampleRepository.findAll();
    }

    // READ example by id
    public Example getOneExample(Integer id) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        return optionalExample.orElse(null);
    }

    // CREATE new example
    public Result addExample(ExampleDTO exampleDTO) {
        // check task
        Optional<Task> optionalTask = taskRepository.findById(exampleDTO.getTaskId());
        if (optionalTask.isEmpty()) {
            return new Result("Task with this id is not found", false);
        }
        Example newExample = new Example();
        newExample.setText(exampleDTO.getText());
        newExample.setTask(optionalTask.get());


        exampleRepository.save(newExample);
        return new Result("Example added", true);

    }

    // UPDATE example by id
    public Result updateExample(Integer id, ExampleDTO exampleDTO) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (optionalExample.isPresent()) {
            // check task
            Optional<Task> optionalTask = taskRepository.findById(exampleDTO.getTaskId());
            if (optionalTask.isEmpty()) {
                return new Result("Task with this id is not found", false);
            }
            Example editedExample = optionalExample.get();
            editedExample.setText(exampleDTO.getText());
            editedExample.setTask(optionalTask.get());


            exampleRepository.save(editedExample);
            return new Result("Example updated", true);
        }
        return new Result("Example with this id is not found", false);
    }

    // DELETE example by id
    public Result deleteExampleById(Integer id) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (optionalExample.isPresent()) {
            exampleRepository.deleteById(id);
            return new Result("Example deleted", true);
        }
        return new Result("Example with this id is not found", false);
    }
}

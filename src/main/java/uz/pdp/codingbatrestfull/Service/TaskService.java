package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.DTO.TaskDTO;
import uz.pdp.codingbatrestfull.Entity.Language;
import uz.pdp.codingbatrestfull.Entity.Task;
import uz.pdp.codingbatrestfull.Repository.LanguageRepository;
import uz.pdp.codingbatrestfull.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;

    // READ all Tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // READ task by id
    public Task getOneTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    // CREATE new task
    public Result addTask(TaskDTO taskDTO) {
        boolean exists = taskRepository.existsByName(taskDTO.getName());
        if (exists) {
            return new Result("This task name already exists", false);
        }

        // check language
        Optional<Language> optionalLanguage = languageRepository.findById(taskDTO.getLanguageId());
        if (optionalLanguage.isEmpty()) {
            return new Result("Language with this id is not found", false);
        }
        Task newTask = new Task();
        newTask.setName(taskDTO.getName());
        newTask.setText(taskDTO.getText());
        newTask.setSolution(taskDTO.getSolution());
        newTask.setHint(taskDTO.getHint());
        newTask.setMethod(taskDTO.getMethod());
        newTask.setHasStar(taskDTO.getHasStar());
        newTask.setLanguage(optionalLanguage.get());

        taskRepository.save(newTask);
        return new Result("Task added", true);

    }

    // UPDATE task by id
    public Result updateTask(Integer id, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            boolean exists = taskRepository.existsByName(taskDTO.getName());
            if (exists) {
                return new Result("This task name already exists", false);
            }

            // check language
            Optional<Language> optionalLanguage = languageRepository.findById(taskDTO.getLanguageId());
            if (optionalLanguage.isEmpty()) {
                return new Result("Language with this id is not found", false);
            }
            Task editedTask = optionalTask.get();
            editedTask.setName(taskDTO.getName());
            editedTask.setText(taskDTO.getText());
            editedTask.setSolution(taskDTO.getSolution());
            editedTask.setHint(taskDTO.getHint());
            editedTask.setMethod(taskDTO.getMethod());
            editedTask.setHasStar(taskDTO.getHasStar());
            editedTask.setLanguage(optionalLanguage.get());

            taskRepository.save(editedTask);
            return new Result("Task updated", true);
        }
        return new Result("Task with this id is not found", false);
    }

    // DELETE task by id
    public Result deleteTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
            return new Result("Task deleted", true);
        }
        return new Result("Task with this id is not found", false);
    }
}


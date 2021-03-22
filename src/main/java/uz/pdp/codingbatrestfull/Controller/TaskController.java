package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.DTO.TaskDTO;
import uz.pdp.codingbatrestfull.Service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    // READ all tasks
    @GetMapping
    public HttpEntity<?> readAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // READ tasks by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getOneTask(id));
    }

    // CREATE new task
    @PostMapping
    public HttpEntity<?> createNewTask(@Valid @RequestBody TaskDTO taskDTO) {
        Result result = taskService.addTask(taskDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE task by id
    @PutMapping("/{id}")
    public HttpEntity<?> editTask(@PathVariable Integer id, @Valid @RequestBody TaskDTO taskDTO) {
        Result result = taskService.updateTask(id, taskDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE task by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTask(@PathVariable Integer id) {
        Result result = taskService.deleteTaskById(id);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // XATOLIKLARNI ANIQLASH
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> mistakes = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mistakes.put(fieldName, errorMessage);
        });
        return mistakes;
    }
}

package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.AnswerDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Service.AnswerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    // READ all answers
    @GetMapping
    public HttpEntity<?> readAllAnswers() {
        return ResponseEntity.ok(answerService.getAllAnswers());
    }

    // READ answer by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneAnswer(@PathVariable Integer id) {
        return ResponseEntity.ok(answerService.getOneAnswer(id));
    }

    // CREATE new answer
    @PostMapping
    public HttpEntity<?> createNewAnswer(@Valid @RequestBody AnswerDTO answerDTO) {
        Result result = answerService.addAnswer(answerDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE answer by id
    @PutMapping("/{id}")
    public HttpEntity<?> editAnswer(@PathVariable Integer id, @Valid @RequestBody AnswerDTO answerDTO) {
        Result result = answerService.updateAnswer(id, answerDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE answer by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteAnswer(@PathVariable Integer id) {
        Result result = answerService.deleteAnswerById(id);
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

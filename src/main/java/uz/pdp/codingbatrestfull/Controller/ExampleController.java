package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.ExampleDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Service.ExampleService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    // READ all examples
    @GetMapping
    public HttpEntity<?> readAllExamples() {
        return ResponseEntity.ok(exampleService.getAllExamples());
    }

    // READ example by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneExample(@PathVariable Integer id) {
        return ResponseEntity.ok(exampleService.getOneExample(id));
    }

    // CREATE new example
    @PostMapping
    public HttpEntity<?> createNewExample(@Valid @RequestBody ExampleDTO exampleDTO) {
        Result result = exampleService.addExample(exampleDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE example by id
    @PutMapping("/{id}")
    public HttpEntity<?> editExample(@PathVariable Integer id, @Valid @RequestBody ExampleDTO exampleDTO) {
        Result result = exampleService.updateExample(id, exampleDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE example by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteExample(@PathVariable Integer id) {
        Result result = exampleService.deleteExampleById(id);
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

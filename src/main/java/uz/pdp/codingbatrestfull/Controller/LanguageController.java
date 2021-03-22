package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Entity.Language;
import uz.pdp.codingbatrestfull.Service.LanguageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/language")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    // READ all languages
    @GetMapping
    public HttpEntity<?> readAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    // READ languages by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneLanguage(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getOneLanguage(id));
    }

    // CREATE new language
    @PostMapping
    public HttpEntity<?> createNewLanguage(@Valid @RequestBody Language language) {
        Result result = languageService.addLanguage(language);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE language by id
    @PutMapping("/{id}")
    public HttpEntity<?> editLanguage(@PathVariable Integer id, @Valid @RequestBody Language language) {
        Result result = languageService.updateLanguage(id, language);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE language by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteLanguage(@PathVariable Integer id) {
        Result result = languageService.deleteLanguageById(id);
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

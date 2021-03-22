package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.CategoryDTO;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.Service.CategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    // READ all categories
    @GetMapping
    public HttpEntity<?> readAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // READ category by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getOneCategory(id));
    }

    // CREATE new category
    @PostMapping
    public HttpEntity<?> createNewCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Result result = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE category by id
    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Result result = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE category by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id) {
        Result result = categoryService.deleteCategoryById(id);
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

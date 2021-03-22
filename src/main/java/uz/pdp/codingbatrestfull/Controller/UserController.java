package uz.pdp.codingbatrestfull.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.DTO.UserDTO;
import uz.pdp.codingbatrestfull.Service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    // READ all users
    @GetMapping
    public HttpEntity<?> readAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // READ user by id
    @GetMapping("/{id}")
    public HttpEntity<?> readOneUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    // CREATE new user
    @PostMapping
    public HttpEntity<?> createNewUser(@Valid @RequestBody UserDTO userDTO) {
        Result result = userService.addUser(userDTO);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }

    // UPDATE user by id
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        Result result = userService.updateUser(id, userDTO);
        return ResponseEntity.status(result.isSuccess() ? 202 : 409).body(result);
    }

    // DELETE user by id
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id) {
        Result result = userService.deleteUserById(id);
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

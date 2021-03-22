package uz.pdp.codingbatrestfull.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatrestfull.DTO.Result;
import uz.pdp.codingbatrestfull.DTO.UserDTO;
import uz.pdp.codingbatrestfull.Entity.Answer;
import uz.pdp.codingbatrestfull.Entity.User;
import uz.pdp.codingbatrestfull.Repository.AnswerRepository;
import uz.pdp.codingbatrestfull.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;

    // READ all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ user by id
    public User getOneUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    // CREATE new user
    public Result addUser(UserDTO userDTO) {
        boolean exists = userRepository.existsByEmail(userDTO.getEmail());
        if (exists){
            return new Result("This email already exists",false);
        }

        // check answer
        Optional<Answer> optionalAnswer = answerRepository.findById(userDTO.getAnswerId());
        if (optionalAnswer.isEmpty()) {
            return new Result("Answer with this id is not found", false);
        }

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setAnswer(optionalAnswer.get());

        userRepository.save(newUser);
        return new Result("User added", true);

    }

    // UPDATE user by id
    public Result updateUser(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            boolean exists = userRepository.existsByEmail(userDTO.getEmail());
            if (exists){
                return new Result("This email already exists",false);
            }

            // check answer
            Optional<Answer> optionalAnswer = answerRepository.findById(userDTO.getAnswerId());
            if (optionalAnswer.isEmpty()) {
                return new Result("Answer with this id is not found", false);
            }

            User editedUser = optionalUser.get();
            editedUser.setEmail(userDTO.getEmail());
            editedUser.setPassword(userDTO.getPassword());
            editedUser.setAnswer(optionalAnswer.get());

            userRepository.save(editedUser);
            return new Result("User updated", true);
        }
        return new Result("User with this id is not found", false);
    }

    // DELETE user by id
    public Result deleteUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return new Result("User deleted", true);
        }
        return new Result("User with this id is not found", false);
    }
}

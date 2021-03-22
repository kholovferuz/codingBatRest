package uz.pdp.codingbatrestfull.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;

    // answer
    private Integer answerId;
}

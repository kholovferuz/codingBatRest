package uz.pdp.codingbatrestfull.DTO;

import lombok.Data;

@Data
public class AnswerDTO {
    private Boolean isCorrect;

    // task
    private Integer taskId;
}

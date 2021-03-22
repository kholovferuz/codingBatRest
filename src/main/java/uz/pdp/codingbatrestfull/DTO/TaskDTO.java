package uz.pdp.codingbatrestfull.DTO;

import lombok.Data;

@Data
public class TaskDTO {
    private String name;
    private String text;
    private String solution;
    private String hint;
    private String method;
    private Boolean hasStar;

    // language
    private Integer languageId;
}

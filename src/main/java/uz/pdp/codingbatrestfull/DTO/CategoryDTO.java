package uz.pdp.codingbatrestfull.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private String name;
    private String description;

    // language
    private List<Integer> languagesId;
}

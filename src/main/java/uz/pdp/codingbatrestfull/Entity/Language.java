package uz.pdp.codingbatrestfull.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

}

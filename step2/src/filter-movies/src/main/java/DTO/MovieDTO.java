package DTO;

import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {

    private String title;
    private int year;
    private List<String> cast;
    private List<String> genres;

}

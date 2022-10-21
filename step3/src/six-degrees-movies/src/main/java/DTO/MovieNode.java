package DTO;

import lombok.Data;

import java.util.List;

@Data
public class MovieNode {

    private MovieNode parent;
    private int movieIndex;
    private String linkedActor;
    private int level;

}

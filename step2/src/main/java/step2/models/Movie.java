package step2.models;

import java.util.List;
public record Movie(String title, Integer year, List<String> cast, List<String> genres) { }

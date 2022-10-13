package org.step3;

import java.util.ArrayList;
import java.util.List;


public class Movie {
    public String title;
    public Long year;
    public ArrayList<String> cast;

    public Movie(String title, Long year, ArrayList<String> cast) {
        this.title = title;
        this.year = year;
        this.cast = cast;
    }

    public List<String> getCast() {
        return cast;
    }

    public String toString() {
        return getClass().getName() + " @ " + title + " / " + year;
    }
}

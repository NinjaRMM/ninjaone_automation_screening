package org.example;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Movie {
    public String title;
    public Integer year;
    public ArrayList<String> cast;

    public Movie(String title, Integer year, ArrayList<String> cast) {
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


/*

"title":"Adam at Six A.M.",
      "year":1970,
      "cast":[
         "Michael Douglas",
         "Lee Purcell",
         "Joe Don Baker",
         "Louise Latham"
      ],
      "genres":[
         "Drama"
      ]

 */
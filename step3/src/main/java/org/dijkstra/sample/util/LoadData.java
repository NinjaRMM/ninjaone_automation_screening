package org.dijkstra.sample.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Observable;
import org.dijkstra.sample.model.Movie;

import java.io.FileReader;

public class LoadData {
    public static Observable<Movie> getObservable(String sourceFilePath) {
        Movie[] data;
        try {
            data = new ObjectMapper().readValue(new FileReader(sourceFilePath),
                    Movie[].class);
            return Observable.fromArray(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}

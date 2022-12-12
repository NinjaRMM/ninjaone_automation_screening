package org.query.json.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Observable;

import java.io.FileReader;

public class Data {
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

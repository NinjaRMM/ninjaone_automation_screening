package org.query.json.app;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Observable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        if (args.length >= 4) {
            int startRange = Integer.parseInt(args[0]);
            int finalRange = Integer.parseInt(args[1]);
            String nameJsonFile = args[2];
            String nameDestinationFile = args[3];
            writeToJson(getMovieByRange(startRange, finalRange, nameJsonFile).toList().blockingGet(), nameDestinationFile);

        }else{
            System.out.println("Invalid parameters number");
        }


    }

    public static Observable<Movie> getMovieByRange(final int startRange, final int finalRange, final String sourceFile) {
        return Objects.requireNonNull(Data.getObservable(sourceFile))
                .filter(a -> a.getYear() >= startRange && a.getYear() <= finalRange);
    }

    public static void writeToJson(List<Movie> movie, String pathFile) {
        String json = new Gson().toJson(movie);
        Path filePath = Path.of(pathFile);
        try (FileOutputStream outputStream
                     = new FileOutputStream(filePath.toFile())) {
            byte[] strToBytes = json.getBytes();
            outputStream.write(strToBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
package com.ninja.one.degreeofseparations.controller;

import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;
import com.ninja.one.degreeofseparations.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies/{decade}")
    public ResponseEntity<List<Movie>> getMoviesFromDecades(@PathVariable Integer decade) throws ApiException {
        return ResponseEntity.ok(movieService.extractMovieFromDecade(decade));
    }

    @GetMapping("/degree-of-separation")
    public ResponseEntity<String> degreeOfSeparation(@RequestParam String actor1,
                                                     @RequestParam(required = false) String actor2) throws ApiException {
        return ResponseEntity.ok(movieService.calculateDegreeOfSeparation(actor1, actor2));
    }
}

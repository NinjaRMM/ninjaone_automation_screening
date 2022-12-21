package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class DecadeMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        Integer decade;
        if ((decade = filter.getDecade()) != null) {
            return movie.getYear() >= decade && movie.getYear() <= getDecadeEnd(decade);
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (filter.getDecade() != null) {
            return Optional.of(Integer.toString(filter.getDecade()));
        }
        return Optional.empty();
    }

    private int getDecadeEnd(Integer decade) {
        return LocalDate.of(decade,1,1)
                .plus(1, ChronoUnit.DECADES)
                .minus(1, ChronoUnit.DAYS).getYear();
    }
}

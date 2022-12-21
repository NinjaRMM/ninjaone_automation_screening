package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

public class GenreMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (CollectionUtils.isNotEmpty(filter.getGenres())) {
            if (CollectionUtils.isNotEmpty(movie.getGenres())) {
                return filter.getGenres().stream().allMatch(filterGenre ->
                        movie.getGenres().stream().anyMatch(movieGenre -> StringUtils.equalsIgnoreCase(movieGenre, filterGenre)));
            }
            return false;
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (CollectionUtils.isNotEmpty(filter.getGenres())) {
            return Optional.of(filter.getGenres().stream().sorted().collect(Collectors.joining("-")));
        }
        return Optional.empty();
    }
}

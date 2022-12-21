package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

public class CastMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (CollectionUtils.isNotEmpty(filter.getCast())) {
            return filter.getCast().stream().allMatch(filterCast ->
                    movie.getCast().stream().anyMatch(movieCast -> StringUtils.equalsIgnoreCase(filterCast, movieCast)));
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (CollectionUtils.isNotEmpty(filter.getCast())) {
            return Optional.of(filter.getCast().stream().sorted().collect(Collectors.joining("-")));
        }
        return Optional.empty();
    }
}

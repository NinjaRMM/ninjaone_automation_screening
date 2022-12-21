package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class StringExactMatchMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (StringUtils.isNotBlank(filter.getExactTitle())) {
            return movie.getTitle().equalsIgnoreCase(filter.getExactTitle());
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (StringUtils.isNotBlank(filter.getExactTitle())) {
            return Optional.of(filter.getExactTitle());
        }
        return Optional.empty();
    }
}

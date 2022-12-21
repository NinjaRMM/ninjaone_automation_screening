package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieFilter {

    private static Set<MovieFilterMatcher> matchers;

    private String exactTitle;
    private String titleContains;
    public Integer afterYear;
    public Integer beforeYear;
    public Integer exactYear;
    public Integer decade;
    private Set<String> cast = new HashSet<>();
    private Set<String> genres = new HashSet<>();

    static {
        matchers = new HashSet<>();
        matchers.add(new StringContainsMovieFilterMatcher());
        matchers.add(new StringExactMatchMovieFilterMatcher());
        matchers.add(new ExactYearMovieFilterMatcher());
        matchers.add(new BeforeYearMovieFilterMatcher());
        matchers.add(new AfterYearMovieFilterMatcher());
        matchers.add(new DecadeMovieFilterMatcher());
        matchers.add(new GenreMovieFilterMatcher());
    }

    public MovieFilter exactTitle(String title) {
        this.exactTitle = title;
        return this;
    }

    public MovieFilter titleContains(String title) {
        this.titleContains = title;
        return this;
    }

    public MovieFilter afterYear(Integer year) {
        validateYear(year);
        this.afterYear = year;
        return this;
    }

    public MovieFilter beforeYear(Integer year) {
        validateYear(year);
        this.beforeYear = year;
        return this;
    }

    public MovieFilter exactYear(Integer year) {
        validateYear(year);
        this.exactYear = year;
        return this;
    }

    public MovieFilter cast(String cast) {
        this.cast.add(cast);
        return this;
    }

    public MovieFilter genre(String genre) {
        this.genres.add(genre);
        return this;
    }

    public MovieFilter decade(Integer decade) {
        validateDecade(decade);
        this.decade = decade;
        return this;
    }

    private void validateDecade(Integer decade) {
    }

    public String getExactTitle() {
        return exactTitle;
    }

    public String getTitleContains() {
        return titleContains;
    }

    public Integer getAfterYear() {
        return afterYear;
    }

    public Integer getBeforeYear() {
        return beforeYear;
    }

    public Integer getExactYear() {
        return exactYear;
    }

    public Set<String> getCast() {
        if (cast != null) {
            return Collections.unmodifiableSet(cast);
        }
        return Collections.emptySet();

    }

    public Set<String> getGenres() {
        if (genres != null) {
            return Collections.unmodifiableSet(genres);
        }
        return Collections.emptySet();
    }

    public Integer getDecade() {
        return this.decade;
    }

    private void validateYear(int year) {
        int currentYear = LocalDateTime.now().getYear();
        if (year < 1900 || year > currentYear) {
            throw new IllegalArgumentException("Year is invalid. It must be greater than 1900 and fewer than " + currentYear);
        }
    }

    private void validateFilters() {
        if ((beforeYear != null && (afterYear != null || exactYear != null)) ||
                (afterYear != null && (exactYear != null))) {
            throw new IllegalArgumentException("Set either beforeYear, afterYear or exactYear");
        }
        if (StringUtils.isNotBlank(titleContains) && StringUtils.isNotBlank(exactTitle)) {
            throw new IllegalArgumentException("Set either titleContains or exactTitle");
        }
    }

    public boolean matches(Movie movie) {
        validateFilters();
        if (movie != null) {
            return matchers.stream().map(m -> m.matches(movie, this)).reduce((m1, m2) -> m1 && m2).orElse(false);
        }
        return false;
    }


    public Path createFileName() {
        String partialName = matchers.stream().map(m -> m.getFilterApplied(this))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .collect(Collectors.joining("-"));

        return Paths.get(String.format("%s-movies.json", partialName));
    }
}

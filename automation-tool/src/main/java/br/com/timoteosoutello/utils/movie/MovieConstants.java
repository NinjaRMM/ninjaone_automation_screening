package br.com.timoteosoutello.utils.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import br.com.timoteosoutello.domain.movie.Movie;

public class MovieConstants {
	public static final Type MOVIE_LIST_TYPE_CONSTANT = new TypeToken<ArrayList<Movie>>() {}.getType();
	public static final String DATA_MOVIES_DIR = "../data/";
	public static final String DEFAULT_ACTOR_NAME = "Kevin Bacon";
}

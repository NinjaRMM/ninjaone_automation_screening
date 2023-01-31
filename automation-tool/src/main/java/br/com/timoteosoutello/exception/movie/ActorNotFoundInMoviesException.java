package br.com.timoteosoutello.exception.movie;

public class ActorNotFoundInMoviesException extends Exception {

	private static final long serialVersionUID = 3543749689098020300L;
	public static final String MESSAGE = "%s did not star in a movie in the data provided.";

	public ActorNotFoundInMoviesException(String actor) {
		super(String.format(MESSAGE, actor));
	}

}

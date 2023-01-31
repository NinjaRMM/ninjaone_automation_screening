package br.com.timoteosoutello.exception.movie;

public class MoviesFilePathNotFoundException extends Exception {

	private static final long serialVersionUID = 9085166732935912793L;
	private static final String MESSAGE = "JSON movies file not found, please inform a valid path.";
	
	public MoviesFilePathNotFoundException() {
		super(MESSAGE);
	}

}

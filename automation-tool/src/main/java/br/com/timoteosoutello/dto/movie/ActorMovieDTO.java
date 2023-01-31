package br.com.timoteosoutello.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActorMovieDTO {
	private String movieTitle;
	private String actorName;

	public ActorMovieDTO(String movieTitle, String actorName) {
		this.movieTitle = movieTitle;
		this.actorName = actorName;
	}
}

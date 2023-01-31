package br.com.timoteosoutello.dto.movie;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SixDegreeActorMovieDTO {

	private String actorName1;
	private String actorName2;
	private int numberOfDegrees;
	private List<ActorMovieDTO> actorMoviesDTOs;

	public SixDegreeActorMovieDTO() {

	}

	public SixDegreeActorMovieDTO(final String actorName1, final String actorName2,
			final List<ActorMovieDTO> actorMoviesDTOs) {
		this.actorName1 = actorName1;
		this.actorName2 = actorName2;
		this.actorMoviesDTOs = actorMoviesDTOs;
		// As the granularity is between the movies and not the actual size of quantity
		// of movies found.
		this.numberOfDegrees = actorMoviesDTOs == null || actorMoviesDTOs.size() == 0 ? 0 : actorMoviesDTOs.size() - 1;
	}

}

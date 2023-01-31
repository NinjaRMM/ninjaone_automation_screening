package br.com.timoteosoutello.domain.movie;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Movie {

	private String title;
	private Integer year;
	private List<String> cast;
	private List<String> genres;

}

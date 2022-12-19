package rubens.step3;

public class ChainElement {

	public ChainElement(String actor, String movie) {
		super();
		this.actor = actor;
		this.movie = movie;
	}

	private String actor;
	private String movie;

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

}

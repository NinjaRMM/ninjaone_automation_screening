package rubens.step3;

public class ActorDidNotStarException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ActorDidNotStarException(String actor) {
		super(String.format("%s did not star in a movie in the data provided", actor));
	}

}

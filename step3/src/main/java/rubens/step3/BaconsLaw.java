package rubens.step3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaconsLaw {

	private static final String MISSING_REQUIRED_ARGUMENTS_ACTOR1 = "You must provide at least one actor";
	private static final String MOVIES_JSON = "80s-movies.json";
	private static final String KEVIN_BACON = "Kevin Bacon";

	Map<String, Set<String>> actors = new HashMap<>();
	Map<String, Set<String>> movies = new HashMap<>();
	Set<String> visitedActors = new HashSet<>();
	Set<String> visitedMovies = new HashSet<>();
	Map<String, ChainElement> chain = new HashMap<>();
	Map<String, Integer> degrees = new HashMap<>();
	Queue<String> queue = new LinkedList<>();

	public Integer calculateDegrees(String[] args) throws IOException {
		FilterInput filterInput = getInputs(args);
		validateFilters(filterInput);

		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jsonFactory = new JsonFactory(mapper);
		JsonParser jsonParser = getJsonParser(jsonFactory);

		loadMoviesAndActors(jsonParser);
		validateActors(filterInput, actors);

		queue.add(filterInput.getActor1());
		visitedActors.add(filterInput.getActor1());
		degrees.put(filterInput.getActor1(), 0);

		while (!queue.isEmpty()) {
			String currentActor = queue.poll();
			Integer degree = visitActor(currentActor, filterInput.getActor2());
			if (degree > -1) {
				buildShorttestPathChain(chain, filterInput.getActor2()).forEach(movie -> System.out.println(movie));
				return degree;
			}
		}

		return -1;
	}
	
	List<String> buildShorttestPathChain(Map<String, ChainElement> fullChain, String targetActor) {
		List<String> chain = new ArrayList<>();
		ChainElement element = fullChain.get(targetActor);
		
		while (element != null) {
			chain.add(element.getMovie());
			element = fullChain.get(element.getActor());
		}
		
		return chain;
	}

	private Integer visitActor(String currentActor, String targetActor) {
		for (String movie : actors.get(currentActor)) {
			if (!visitedMovies.contains(movie)) {
				visitedMovies.add(movie);
				for (String actor : movies.get(movie)) {
					if (!visitedActors.contains(actor)) {
						visitedActors.add(actor);
						queue.add(actor);
						degrees.put(actor, degrees.get(currentActor) + 1);
						chain.put(actor, new ChainElement(currentActor, movie));
					}
					
					if (targetActor.equals(actor)) {
						return degrees.get(currentActor);
					}
				}
			}
		}

		return -1;
	}

	private void loadMoviesAndActors(JsonParser jsonParser) throws IOException {
		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			JsonToken token = jsonParser.getCurrentToken();
			if (token == JsonToken.START_OBJECT) {
				processJsonNode(jsonParser);
			}
		}
	}

	private void processJsonNode(JsonParser jsonParser) throws IOException {
		JsonNode node = jsonParser.readValueAsTree();
		String movie = node.get("title").asText();
		Set<String> currentMovieActors = new HashSet<>();
		node.get("cast").iterator().forEachRemaining(a -> {
			String actor = a.asText();
			if (!actors.containsKey(actor)) {
				actors.put(actor, new HashSet<>());
			}
			actors.get(actor).add(movie);
			currentMovieActors.add(actor);
		});
		movies.put(movie, currentMovieActors);
	}

	private JsonParser getJsonParser(JsonFactory jsonFactory) throws IOException {
		InputStream is = BaconsLaw.class.getClassLoader().getResourceAsStream(MOVIES_JSON);
		return jsonFactory.createParser(is);
	}

	private FilterInput getInputs(String[] args) {
		if (args == null || args.length == 0) {
			throw new IllegalArgumentException(MISSING_REQUIRED_ARGUMENTS_ACTOR1);
		}

		String arg = String.join(" ", args);
		String[] splittedArgs = arg.split(",");

		String actor1 = splittedArgs[0];
		String actor2 = splittedArgs.length > 1 ? splittedArgs[1] : KEVIN_BACON;

		return new FilterInput(actor1, actor2);
	}

	private void validateFilters(FilterInput filterInput) {
		if (filterInput.getActor1() == null) {
			throw new IllegalArgumentException(MISSING_REQUIRED_ARGUMENTS_ACTOR1);
		}

		if (filterInput.getActor1().equals(filterInput.getActor2())) {
			throw new IllegalArgumentException("actor1 and actor2 must be different");
		}
	}

	private void validateActors(FilterInput filterInput, Map<String, Set<String>> actors) {
		if (!actors.containsKey(filterInput.getActor1())) {
			throw new ActorDidNotStarException(filterInput.getActor1());
		}

		if (!actors.containsKey(filterInput.getActor2())) {
			throw new ActorDidNotStarException(filterInput.getActor2());
		}
	}

	public static void main(String[] args) throws IOException {
		BaconsLaw baconsLaw = new BaconsLaw();
		try {
			Integer degrees = baconsLaw.calculateDegrees(args);
			System.out.println(degrees);
		} catch (ActorDidNotStarException e) {
			System.out.println(e.getMessage());
		}

	}

}

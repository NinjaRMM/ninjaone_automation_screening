package rubens.step2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieFilterTest {
	
	private MovieFilter movieFilter = new MovieFilter();
	
	
	@Test
	public void shouldThrowExceptionWhenDecadeIsAbsent() {
		String[] args = {"--output=filtered-movies.json"};
		assertThrows(IllegalArgumentException.class, () -> movieFilter.run(args));
	}
	
	@Test
	public void shouldThrowExceptionWhenOutputIsAbsent() {
		String[] args = {"--decade=80"};
		assertThrows(IllegalArgumentException.class, () -> movieFilter.run(args));
	}
	
	@Test
	public void shouldThrowExceptionWhenDecadeIsNegative() {
		String[] args = {"--decade=-1", "--output=filtered-movies.json"};
		assertThrows(IllegalArgumentException.class, () -> movieFilter.run(args));
	}
	
	@Test
	public void shouldThrowExceptionWhenDecadeIsNotDivisibleBy10() {
		String[] args = {"--decade=17", "--output=filtered-movies.json"};
		assertThrows(IllegalArgumentException.class, () -> movieFilter.run(args));
	}
	
	@Test
	public void shouldReturnEmpty() throws IOException {
		String outputPath = getOutputPath();
		String[] args = {"--decade=70", String.format("--output=%s", outputPath)};
		movieFilter.run(args);
		assertJsonArrayLength(outputPath, 0);
	}
	
	@Test
	public void shouldReturnOneMovie() throws IOException {
		String outputPath = getOutputPath();
		String[] args = {"--decade=90", String.format("--output=%s", outputPath)};
		movieFilter.run(args);
		assertJsonArrayLength(outputPath, 1);
	}
	
	@Test
	public void shouldReturnTwoMovies() throws IOException {
		String outputPath = getOutputPath();
		String[] args = {"--decade=80", String.format("--output=%s", outputPath)};
		movieFilter.run(args);
		assertJsonArrayLength(outputPath, 2);
	}
	
	private String getOutputPath() throws IOException {
		Path tempDir = Files.createTempDirectory("step2-tests");
		return String.format("%s/filtered-movies.json", tempDir);
	}
	
	private void assertJsonArrayLength(String jsonPath, Integer expectedLength) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jsonFactory = new JsonFactory(mapper);
		JsonParser jsonParser = jsonFactory.createParser(new File(jsonPath));
		Integer length = 0;
		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			JsonToken token = jsonParser.getCurrentToken();
			if (token == JsonToken.START_OBJECT) {
				jsonParser.readValueAsTree();
				length++;
			}
		}
		assertEquals(expectedLength, length);
	}

}

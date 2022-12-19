package rubens.step3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class BaconsLawTest {

	@Spy
	private BaconsLaw baconsLaw;
	
	@Before
	public void setup() {
		baconsLaw = Mockito.spy(new BaconsLaw());
	}
	
	@Test
	public void shouldThrowExceptionWhenActor1IsNotProvided() {
		assertThrows(IllegalArgumentException.class, () -> baconsLaw.calculateDegrees(null));
	}
	
	@Test
	public void shouldThrowExceptionWhenActor1EqualsActor2() {
		String[] args = {"Kevin Bacon,Kevin Bacon"};
		assertThrows(IllegalArgumentException.class, () -> baconsLaw.calculateDegrees(args));
	}
	
	@Test
	public void shouldReturnDegree1BetweenKevinBaconAndTomCruise() throws IOException {
		String[] args = {"Tom Cruise"};
		assertEquals(new Integer(1), baconsLaw.calculateDegrees(args));
		
		List<String> chain = baconsLaw.buildShorttestPathChain(baconsLaw.chain, "Kevin Bacon");
		assertEquals(chain.get(0), "Footloose");
		assertEquals(chain.get(1), "All the Right Moves");
	}
	
	@Test
	public void shouldReturnDegree2BetweenTomCruiseAndSylvesterStallone() throws IOException {
		String[] args = {"Tom Cruise,Sylvester Stallone"};
		assertEquals(new Integer(2), baconsLaw.calculateDegrees(args));
		
		List<String> chain = baconsLaw.buildShorttestPathChain(baconsLaw.chain, "Sylvester Stallone");
		assertEquals(chain.get(0), "Nighthawks");
		assertEquals(chain.get(1), "The Osterman Weekend");
		assertEquals(chain.get(2), "All the Right Moves");
	}
	
	@Test
	public void shouldShowErrorMessageWhenTheOnlyActorIsNotFound() throws IOException {
		String[] args = {"Buzz Lightyear"};
		ActorDidNotStarException e = assertThrows(ActorDidNotStarException.class, () -> baconsLaw.calculateDegrees(args));
		assertEquals("Buzz Lightyear did not star in a movie in the data provided", e.getMessage());
	}
	
	@Test
	public void shouldShowErrorMessageWhenOneActorIsNotFound() throws IOException {
		String[] args = {"Buzz Lightyear,Tom Cruise"};
		ActorDidNotStarException e = assertThrows(ActorDidNotStarException.class, () -> baconsLaw.calculateDegrees(args));
		assertEquals("Buzz Lightyear did not star in a movie in the data provided", e.getMessage());
	}
	
}

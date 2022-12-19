package rubens.step3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

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
	public void shouldMatchExecutionSet() throws IOException {
		String[] args1 = {"Tom Cruise,Kevin Bacon"};
		assertEquals(new Integer(2), baconsLaw.calculateDegrees(args1));
		
		String[] args2 = {"Tom Cruise,Sylvester Stallone"};
		assertEquals(new Integer(2), baconsLaw.calculateDegrees(args2));
		
		String[] args3 = {"Buzz Lightyear"};
		ActorDidNotStarException e1 = assertThrows(ActorDidNotStarException.class, () -> baconsLaw.calculateDegrees(args3));
		assertEquals("Buzz Lightyear did not star in a movie in the data provided", e1.getMessage());
		
		String[] args4 = {"Buzz Lightyear", "Tom Cruise"};
		ActorDidNotStarException e2 = assertThrows(ActorDidNotStarException.class, () -> baconsLaw.calculateDegrees(args4));
		assertEquals("Buzz Lightyear did not star in a movie in the data provided", e2.getMessage());
	}
	
}

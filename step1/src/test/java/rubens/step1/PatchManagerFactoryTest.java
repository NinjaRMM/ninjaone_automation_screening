package rubens.step1;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class PatchManagerFactoryTest {
	
	@Spy
	private PatchManagerFactory factory;
	
	@Before
	public void setup() {
		factory = Mockito.spy(new PatchManagerFactory());
	}
	
	@Test
	public void createShouldReturnMacInstance() {
		Mockito.when(factory.getOsName()).thenReturn("Mac");
		
		assertTrue(factory.create() instanceof MacPatchManager);
	}
	
	@Test
	public void createShouldReturnWindowsInstance() {
		Mockito.when(factory.getOsName()).thenReturn("Windows");
		
		assertTrue(factory.create() instanceof WindowsPatchManager);
	}
	
	@Test
	public void createShouldThrowExceptionWhenOSIsNotSupported() {
		Mockito.when(factory.getOsName()).thenReturn("Linux");
		
		RuntimeException thrown = assertThrows(RuntimeException.class, () -> factory.create());
		assertEquals("OS not supported", thrown.getMessage());
	}
}

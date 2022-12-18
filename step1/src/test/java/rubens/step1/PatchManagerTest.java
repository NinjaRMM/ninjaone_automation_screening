package rubens.step1;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class PatchManagerTest {

	@Spy
	private PatchManager patchManager;

	@Before
	public void setup() {
		patchManager = Mockito.spy(new WindowsPatchManager());
	}

	@Test
	public void executeShouldVerifyAutoUpdate() throws IOException {
		Mockito.doNothing().when(patchManager).printInstalledPatches();
		Mockito.doNothing().when(patchManager).printAutoUpdateVerification();
		
		String[] args = {"--verifyAutoUpdate"};
		patchManager.execute(args);
		
		Mockito.verify(patchManager).printInstalledPatches();
		Mockito.verify(patchManager).printAutoUpdateVerification();
	}

	@Test
	public void executeShouldNotVerifyAutoUpdateWhenNoArgsArePassed() throws IOException {
		Mockito.doNothing().when(patchManager).printInstalledPatches();
		
		String[] args = {};
		patchManager.execute(args);
		
		Mockito.verify(patchManager).printInstalledPatches();
		Mockito.verify(patchManager, Mockito.never()).printAutoUpdateVerification();
	}
	
	@Test
	public void executeShouldNotVerifyAutoUpdateWhenUnknownArgsArePassed() throws IOException {
		Mockito.doNothing().when(patchManager).printInstalledPatches();
		
		String[] args = {"--unknown"};
		patchManager.execute(args);
		
		Mockito.verify(patchManager).printInstalledPatches();
		Mockito.verify(patchManager, Mockito.never()).printAutoUpdateVerification();
	}
}

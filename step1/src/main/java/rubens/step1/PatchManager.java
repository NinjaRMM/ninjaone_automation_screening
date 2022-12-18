package rubens.step1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class PatchManager {
	
	private static final String VERIFY_AUTO_UPDATE = "--verifyAutoUpdate";

	public abstract String getInstalledPatchesCommand();
	public abstract String getAutoUpdateVerificationCommand();
	
	public void printInstalledPatches() throws IOException {
		printCommand(getInstalledPatchesCommand());
	}
	
	public void printAutoUpdateVerification() throws IOException {
		System.out.println("-----------------------------");
		System.out.print("Auto Update enabled: ");
		printCommand(getAutoUpdateVerificationCommand());
	}
	
	void execute(String[] args) throws IOException {
		printInstalledPatches();
		if (args.length > 0 && VERIFY_AUTO_UPDATE.equals(args[0])) {
			printAutoUpdateVerification();
		}
	}
	
	private void printCommand(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		PatchManagerFactory factory = new PatchManagerFactory();
		PatchManager patchManager = factory.create();
		patchManager.execute(args);
	}
	
}

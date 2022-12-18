package rubens.step1;

public class PatchManagerFactory {

	public PatchManager create() {
		String osName = getOsName();
		
		if (osName.startsWith("Mac")) {
			return new MacPatchManager();
		} else if (osName.startsWith("Windows")) {
			return new WindowsPatchManager();
		}
		
		throw new RuntimeException("OS not supported");
 	}
	
	String getOsName() {
		return System.getProperty("os.name");
	}
}

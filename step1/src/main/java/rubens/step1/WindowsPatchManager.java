package rubens.step1;

public class WindowsPatchManager extends PatchManager {

	@Override
	public String getInstalledPatchesCommand() {
		return "wmic qfe list";
	}
	
	@Override
	public String getAutoUpdateVerificationCommand() {
		return "reg query \\\"HKEY_LOCAL_MACHINE\\\\Software\\\\Policies\\\\Microsoft\\\\Windows\\\\WindowsUpdate\\\\AU\\\" /v NoAutoUpdate";
	}

}

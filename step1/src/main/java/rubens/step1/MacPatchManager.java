package rubens.step1;

public class MacPatchManager extends PatchManager {

	@Override
	public String getInstalledPatchesCommand() {
		return "softwareupdate --history";
	}

	@Override
	public String getAutoUpdateVerificationCommand() {
		return "defaults read /Library/Preferences/com.apple.SoftwareUpdate AutomaticCheckEnabled";
	}
}

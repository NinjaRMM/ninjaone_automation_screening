package br.com.gbvbahia.interview.shell;

public class WindowsShellExecuter extends ShellExecuter{


	private String[] SYSTEM_INFO_POWER_SHELL = {"powershell.exe", "-Command", "Get-Hotfix"}; 
	//private final String SYSTEM_INFO = "systeminfo.exe"; 
	
	@Override
	public String queryForPatchesInstalled() {
		Process process = executeCommands(SYSTEM_INFO_POWER_SHELL);
		String result = commandResultToString(process);
		
		LOG.info(result);
		
		return EXECUTION_FINISHD;
	}

	
}

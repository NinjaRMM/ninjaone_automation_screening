package br.com.gbvbahia.interview.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public abstract class ShellExecuter {

	protected static final Logger LOG = Logger.getLogger(ShellExecuter.class.getName());
	protected static final String EXECUTION_FINISHD = "Execution Finishd";
	
	private static final String SYS_OS_NAME = "os.name";
	private static final String PATH_TO_WORKING_DIR = "C:\\Windows\\System32";
	private static final String PATH_TO_LOG_DIR = ".\\log";

	public static ShellExecuter factory() {

		String osName = System.getProperty(SYS_OS_NAME);
		OS os = OS.getByOsName(osName);
		LOG.log(Level.INFO, String.format("OS Detected: %s", os));

		switch (os) {

		case WINDOWS:
			return new WindowsShellExecuter();

		default:
			throw new IllegalStateException(String.format("Unknown operating system: %s", osName));
		}

	}

	public abstract String queryForPatchesInstalled();

	protected Process executeCommands(String... commands) {
		Process process = null;
		ProcessBuilder pb = new ProcessBuilder(commands);
		File execDir = getDir(PATH_TO_WORKING_DIR);
		pb.directory(execDir);
		File logDir = getDir(PATH_TO_LOG_DIR);
		pb.redirectError(new File(logDir, "log.log"));

		try {
			process = pb.start();

		} catch (IOException e) {
			String error = String.format("Error: %s creating ProcessBuilder.", e.getMessage());
			LOG.log(Level.SEVERE, error, e);
			throw new RuntimeException(error, e);
		}
		return process;
	}

	private File getDir(String dir) {
		File execDir = new File(dir);
		execDir.mkdir();
		return execDir;
	}

	protected void printCommandResult(Process process) {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			
			while ((line = reader.readLine()) != null) {
				LOG.log(Level.INFO, line);
			}
			
			reader.close();
			int exitVal = process.waitFor();
			
			if (exitVal != 0) {
				LOG.log(Level.WARNING, "The command execution finished with an error.");
			}

		} catch (IOException | InterruptedException e) {
			String error = String.format("Error: %s Printing Process.", e.getMessage());
			LOG.log(Level.SEVERE, error, e);
			throw new RuntimeException(error, e);
		}
	}
	
	protected String commandResultToString(Process process) {
		try {
			
			
			return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);

		} catch (IOException e) {
			String error = String.format("Error: %s Printing Process.", e.getMessage());
			LOG.log(Level.SEVERE, error, e);
			throw new RuntimeException(error, e);
		}
	}
}

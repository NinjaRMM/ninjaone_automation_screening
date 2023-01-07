package br.com.gbvbahia.interview;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import br.com.gbvbahia.interview.shell.ShellExecuter;

public class MainClass {

	public static void main(String[] args) throws Exception {
		InputStream is = MainClass.class.getResourceAsStream("/logging.properties");
		LogManager.getLogManager().readConfiguration(is);
		final Logger LOG = Logger.getLogger(MainClass.class.getName());
		
		ShellExecuter executer = ShellExecuter.factory();
		LOG.info(executer.queryForPatchesInstalled());
	}

}

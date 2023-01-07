package br.com.gbvbahia.interview.shell;

public enum OS {

	WINDOWS(new String[] { "Windows" }),
	NOT_FOUND(new String[] {"OS Not Found"});

	protected final String[] osNames;

	OS(String[] osNames) {
		this.osNames = osNames;
	}

	public static OS getByOsName(String osName) {
		for (OS os : OS.values()) {
			if (NOT_FOUND.equals(os)) {
				continue;
			}
			for (String names : os.osNames) {
				if (osName.contains(names)) {
					return os;
				}
			}
		}
		return NOT_FOUND;
	}
}

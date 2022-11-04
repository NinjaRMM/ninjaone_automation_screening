package com.testTask.ninja.enums;

public enum OperationSystem {
    WINDOWS,
    MAC_OS,
    LINUX;

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String ERROR_MESSAGE = "Sorry, operational system %s is not supported.";

    public static OperationSystem getCurrentOs() {
        if (OS.contains("win")) {
            return WINDOWS;
        } else if (OS.contains("mac")) {
            return MAC_OS;
        } else if (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0) {
            return LINUX;
        } else {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE, System.getProperty("os.name")));
        }
    }
}

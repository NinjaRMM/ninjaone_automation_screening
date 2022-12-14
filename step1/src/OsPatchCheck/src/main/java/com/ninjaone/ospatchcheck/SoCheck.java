package com.ninjaone.ospatchcheck;

public class SoCheck {

    private static final String OPERATINGSYSTEM = System.getProperty("os.name").toLowerCase();
    private static final String VERSION = System.getProperty("os.version").toLowerCase();
    private static final String ARCHITECT = System.getProperty("os.arch").toLowerCase();

    public static void run() {

        if (isWindows()) {
            verifyWindows();
        } else if (isMac()) {
            verifyMac();
        } else if (isUnix()) {
            verifyUnix();
        } else if (isSolaris()) {
            verifySolaris();
        } else {
            System.out.println("Unknown operating system: " + OPERATINGSYSTEM + " v" + VERSION + " (" + ARCHITECT + ")");
        }

    }

    //region internal methods
    private static boolean isWindows() {
        return (OPERATINGSYSTEM.contains("win"));
    }

    private static boolean isMac() {
        return (OPERATINGSYSTEM.contains("mac"));
    }

    private static boolean isUnix() {
        return (OPERATINGSYSTEM.contains("nix") || OPERATINGSYSTEM.contains("nux") || OPERATINGSYSTEM.contains("aix") );
    }

    private static boolean isSolaris() {
        return (OPERATINGSYSTEM.contains("sunos"));
    }

    private static void verifyWindows() {
        System.out.println("Patches Validations");
    }
    private static void verifyMac() {
        System.out.println("Patches Validations");
    }
    private static void verifyUnix() {
        System.out.println("Patches Validations");
    }
    private static void verifySolaris() {
        System.out.println("Patches Validations");
    }
    //endregion

}

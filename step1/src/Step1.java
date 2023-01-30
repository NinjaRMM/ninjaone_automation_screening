import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Step1 {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_WINDOWS = OS_NAME.contains("win");
    private static final boolean IS_LINUX = OS_NAME.contains("nux");
    private static final boolean IS_MAC_OS = OS_NAME.contains("mac");

    public static void main(String[] args) {
        printOsPatches();
    }

    private static void printOsPatches() {
        try{
            if (IS_WINDOWS) {
                printWindowsDetails();
            } else if (IS_LINUX) {
                printLinuxDetails();
            } else if (IS_MAC_OS) {
                printMacOsDetails();
            } else {
                throw new UnsupportedOperationException("OS not supported!");
            }
        } catch (IOException e) {
            System.out.print("Error loading OS patches!");
            e.printStackTrace();
        }
    }

    private static void printWindowsDetails() throws IOException {
        System.out.println("Is Windows Auto Update enabled? " + (isWindowsAutoUpdateEnabled() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Windows OS patches:");
        String[] commands = new String[] {"wmic", "qfe", "list"};
        Process result = executeCommands(commands);
        printDetails(result.getInputStream());
    }

    private static void printLinuxDetails() throws IOException {
        System.out.println("Is Linux Auto Update enabled? " + (isLinuxAutoUpdateEnabled() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Linux OS patches:");
        String[] commands = new String[] {"apt", "list", "--installed"};
        Process result = executeCommands(commands);
        printDetails(result.getInputStream());
    }

    private static void printMacOsDetails() throws IOException {
        System.out.println("Is MoacOS Auto Update enabled? " + (isMacOsAutoUpdateEnabled() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Mac OS patches:");
        String[] commands = new String[] {"softwareupdate", "--history"};
        Process result = executeCommands(commands);
        printDetails(result.getInputStream());
    }

    private static boolean isWindowsAutoUpdateEnabled() throws IOException {
        String[] commands = new String[] {"sc", "query", "wuauserv", "|", "FIND", "\"RUNNING\""};
        Process result = executeCommands(commands);
        BufferedReader reader = new BufferedReader(new InputStreamReader(result.getInputStream()));

        String line = "";

        while((line = reader.readLine()) != null && line.contains("RUNNING")) {
            return true;
        }

        return false;
    }

    private static boolean isLinuxAutoUpdateEnabled() throws IOException {
        String[] commands = new String[] {"cat", "/etc/apt/apt.conf.d/20auto-upgrades"};
        Process result = executeCommands(commands);
        BufferedReader reader = new BufferedReader(new InputStreamReader(result.getInputStream()));

        String line = "";

        while((line = reader.readLine()) != null) {
            if (line.contains("APT::Periodic::Unattended-Upgrade \"1\"")) {
                return true;
            }
        }

        return false;
    }

    private static boolean isMacOsAutoUpdateEnabled() throws IOException {
        String[] commands = new String[] {"/usr/bin/defaults", "read",  "/Library/Preferences/com.apple.SoftwareUpdate.plist"};
        Process result = executeCommands(commands);
        BufferedReader reader = new BufferedReader(new InputStreamReader(result.getInputStream()));

        String line = "";

        while((line = reader.readLine()) != null && line.contains("AutomaticallyInstallMacOSUpdates")) {
            return line.contains("1");
        }

        return false;
    }

    private static Process executeCommands(String[] commands) throws IOException {
        try {
            return new ProcessBuilder(commands).start();
        } catch (IOException e) {
            System.out.println("Error trying to execute commands: " + Arrays.toString(commands));
            throw e;
        }
    } 

    private static void printDetails(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
        }
    }
}


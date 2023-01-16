package step1.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSQuery {

    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        printResults(queryListOfInstalledUpdates(processBuilder));
        printResults(queryCheckStatusWindowsUpdate(processBuilder));
    }

    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("win");
    }

    private static Process queryListOfInstalledUpdates(ProcessBuilder processBuilder) throws IOException {
        if (isWindows()) {
            processBuilder.command("wmic", "qfe", "list", "brief");
        } else processBuilder.command("yum", "update", "-simulate");

        System.out.println("List of Installed Updates: ");
        return processBuilder.start();
    }

    private static Process queryCheckStatusWindowsUpdate(ProcessBuilder processBuilder) throws IOException {
        if (isWindows()) {
            processBuilder.command("sc", "query", "wuauserv");
        }

        System.out.println("Checking Windows Update status: ");
        return processBuilder.start();
    }

    private static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

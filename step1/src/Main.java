import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.name");
        System.out.println("OS: " + osName);
        System.out.println("OS Version: " + osVersion);
        System.out.println("Installed patches: ");
        Process p = Runtime.getRuntime().exec("wmic qfe list");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("Is Automatic Updates Enabled "+  isAutomaticUpdatesEnabled());

    }

    public static boolean isAutomaticUpdatesEnabled() {
        try {
            Process p = Runtime.getRuntime().exec("reg query HKLM\\SOFTWARE\\Policies\\Microsoft\\Windows\\WindowsUpdate /v AutoUpdate");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("AutoUpdate    REG_DWORD    0x1")) {
                    return true;
                } else if (line.contains("AutoUpdate    REG_DWORD    0x")) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
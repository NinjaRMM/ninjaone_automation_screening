package com.ninjaone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

public class QueryOSPatches {
    public static void main(String[] args) {
        String OS = System.getProperty("os.name").toLowerCase();
        String command = "";
        if (OS.contains("win")) {
            command = "powershell \"Get-Hotfix\"";
        } else if (OS.contains("mac")) {
            command = "softwareupdate --list";
        } else {
            System.out.println("OS not supported.");
            return;
        }
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (OS.contains("win")) {
            boolean isAutomaticUpdatesEnabled = isAutomaticUpdatesEnabled();
            System.out.println("Automatic Updates are " + (isAutomaticUpdatesEnabled ? "enabled." : "disabled."));
        }
    }

    public static boolean isAutomaticUpdatesEnabled() {
        Preferences key = Preferences.userRoot().node("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\WindowsUpdate\\Auto Update");
        int value = key.getInt("AUOptions", 0);
        return (value != 1);
    }
}

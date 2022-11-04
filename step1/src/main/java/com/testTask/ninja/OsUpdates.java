package com.testTask.ninja;

import com.testTask.ninja.enums.OperationSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.testTask.ninja.enums.OperationSystem.LINUX;
import static com.testTask.ninja.enums.OperationSystem.WINDOWS;

public class OsUpdates {
    private static final String MESSAGE = "Need to implement logic for %s";
    private static final String MAC_PATH = "/Library/Receipts/InstallHistory.plist";

    public static List<String> getSystemUpdates() {
        OperationSystem currentOs = OperationSystem.getCurrentOs();
        switch (currentOs) {
            case WINDOWS:
                throw new IllegalArgumentException(String.format(MESSAGE, WINDOWS));
            case LINUX:
                throw new IllegalArgumentException(String.format(MESSAGE, LINUX));
            default:
                return getMacUpdates();
        }
    }

    private static List<String> getMacUpdates() {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MAC_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<string>macOS ") & line.contains("</string>")) {
                    line = line.replace("<string>", "");
                    line = line.replace("</string>", "");
                    result.add(line.trim());
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main {
    private final static String INTERPRETER = "powershell.exe";

    public static void main(String[] args) {
        getUpdates();
    }

    public static void getUpdates() {
        CLIHelper.runCommand(INTERPRETER, "gwmi win32_quickfixengineering");
    }


}
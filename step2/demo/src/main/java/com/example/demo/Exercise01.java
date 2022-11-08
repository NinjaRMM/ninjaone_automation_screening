package com.example.demo;

import java.util.Locale;

public class Exercise01 {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        String version = System.getProperty("os.version");
        System.out.println("Operative System: " + os + " " + version);
    }

}

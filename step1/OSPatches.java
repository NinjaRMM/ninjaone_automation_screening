package com.ninja.one.degreeofseparations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OSPatches {

    private static final Logger LOGGER = Logger.getLogger(OSPatches.class.getName());

    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("wmic qfe list");
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.isEmpty()) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}

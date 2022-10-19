package com.ninjaone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
	    String os = System.getProperty("os.name");
	    System.out.println(os);

	    ProcessBuilder processBuilder = new ProcessBuilder();

	    if (os.contains("Mac OS")){
	        String[] command = {"softwareupdate", "--history"};
	        processBuilder.command(command);
        } else if (os.contains("Windows")){
	        String[] command = {"wmic","qfe","list","full"};
            processBuilder.command(command);
        }

	    try{
	        Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }

            int exit = process.waitFor();

            if (exit == 0){
                System.out.println("Ok!, we are done here...");
                System.exit(0);
            } else {
                System.out.println("Ups ->"+exit);
            }
        } catch (Exception e){
	        System.err.println(e.getMessage());
        }
    }
}

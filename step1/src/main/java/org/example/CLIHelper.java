package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLIHelper {
    public static final String MSG_NOT_POSSIBLE_TO_RUN_CMD =
            "Not possible to run command";

    public static void runCommand(String interpreter, String cmd) {
        List<String> cmdList = new ArrayList<>();
        cmdList.add(interpreter);
        cmdList.addAll(Arrays.asList(cmd.split(" ")));
        ProcessBuilder builder = new ProcessBuilder(cmdList.toArray(new String[cmdList.size()]));
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(MSG_NOT_POSSIBLE_TO_RUN_CMD);
        }


    }
}

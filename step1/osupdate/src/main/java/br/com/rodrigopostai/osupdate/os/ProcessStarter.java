package br.com.rodrigopostai.osupdate.os;

import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessStarter {

    private List<String> commands;

    private ProcessStarter() {

    }

    private ProcessStarter(List<String> commands) {
        this.commands = new ArrayList<>();
    }

    public static ProcessStarterBuilder newBuilder() {
        return new ProcessStarterBuilder();
    }

    public InputStream start() throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        for (String command: commands) {
            builder.command(command);
        }
        builder.directory(new File(System.getProperty("user.home")));
        Process start = builder.start();
        return start.getInputStream();
    }

    public static class ProcessStarterBuilder {
        private List<String> commands = new ArrayList<>();
        public ProcessStarterBuilder command(String command) {
            this.commands.add(command);
            return this;
        }
        public ProcessStarter build() {
            if (CollectionUtils.isEmpty(commands)) {
                throw new RuntimeException("Commands are mandatory");
            }
            return new ProcessStarter(commands);
        }
    }

}

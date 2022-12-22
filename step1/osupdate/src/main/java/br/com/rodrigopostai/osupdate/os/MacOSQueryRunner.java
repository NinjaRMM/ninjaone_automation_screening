package br.com.rodrigopostai.osupdate.os;

import br.com.rodrigopostai.osupdate.domain.OSUpdate;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MacOSQueryRunner implements OSQueryRunner{
    @Override
    public List<OSUpdate> search() throws IOException {
        validateOS(OSQueryRunner.getOS());
        List<OSUpdate> updates = new ArrayList<>();

        ProcessStarter.ProcessStarterBuilder builder = ProcessStarter.newBuilder();
        ProcessStarter starter = builder.command("sh").command("-c").command("softwareupdate").command("--history").build();
        InputStream result = starter.start();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result))) {
            String line;
            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if (i > 1) {
                    updates.add(parseLine(line));
                }
                i++;
            }
        }
        return updates;
    }

    private OSUpdate parseLine(String line) {
        if (StringUtils.isNotBlank(line)) {
            String title=StringUtils.trim(StringUtils.substring(line, 0, 51));
            String version = StringUtils.trim(StringUtils.substring(line, 51,61));
            String date = StringUtils.trim(StringUtils.substring(line, 62));
            return new OSUpdate(title, version,date);
        }
        return null;
    }

    private void validateOS(String os) {
        if (!StringUtils.containsIgnoreCase(os, "Mac")) {
            throw new RuntimeException("OS is not Mac: " + os);
        }
    }
}

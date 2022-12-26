package br.com.rodrigopostai.osupdate.os;

import br.com.rodrigopostai.osupdate.domain.OSUpdate;

import java.io.IOException;
import java.util.List;

public interface OSQueryRunner {
    static String getOS() {
        return System.getProperty("os.name");
    }
    List<OSUpdate> search() throws IOException;
}

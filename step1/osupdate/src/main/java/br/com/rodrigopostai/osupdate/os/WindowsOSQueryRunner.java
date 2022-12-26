package br.com.rodrigopostai.osupdate.os;

import br.com.rodrigopostai.osupdate.domain.OSUpdate;

import java.io.IOException;
import java.util.List;

public class WindowsOSQueryRunner implements OSQueryRunner {
    @Override
    public List<OSUpdate> search() throws IOException {
        throw new RuntimeException("Not implemented for Windows yet!!!");
    }
}

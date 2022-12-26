package br.com.rodrigopostai.osupdate;

import br.com.rodrigopostai.osupdate.os.OSQueryRunner;
import br.com.rodrigopostai.osupdate.os.ProcessStarter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OSUpdateApplicationTest {

    private static final String NEW_LINE = System.lineSeparator();

    private MockedStatic<OSQueryRunner> osQueryRunnerMockedStatic;
    MockedStatic<ProcessStarter> processStarterMockedStatic;

    @BeforeEach
    void setup() {
        osQueryRunnerMockedStatic = Mockito.mockStatic(OSQueryRunner.class);
        processStarterMockedStatic = Mockito.mockStatic(ProcessStarter.class);
    }

    @AfterEach
    void tearDown() {
        osQueryRunnerMockedStatic.close();
        processStarterMockedStatic.close();
    }

    @Test
    void shouldListMacOSUpdates() throws Throwable {

        Mockito.when(OSQueryRunner.getOS()).thenReturn("Mac");

        ProcessStarter.ProcessStarterBuilder processStarterBuilder = Mockito.mock(ProcessStarter.ProcessStarterBuilder.class);
        Mockito.when(ProcessStarter.newBuilder()).thenReturn(processStarterBuilder);
        ProcessStarter starter = Mockito.mock(ProcessStarter.class);
        Mockito.when(processStarterBuilder.command(Mockito.anyString())).thenReturn(processStarterBuilder);
        Mockito.when(processStarterBuilder.build()).thenReturn(starter);
        Mockito.when(starter.start()).thenAnswer((i) -> {
            List<String> updates = new ArrayList<>();
            updates.add("Display Name                                       Version    Date" + NEW_LINE);
            updates.add("------------                                       -------    ----" + NEW_LINE);
            updates.add("Command Line Tools for Xcode                       12.4       02/09/2021 17:34:13" + NEW_LINE);
            updates.add("Command Line Tools for Xcode                       12.5       02/09/2021 17:34:13" + NEW_LINE);
            updates.add("macOS Big Sur 11.6                                 11.6       13/09/2021 15:32:58" + NEW_LINE);
            updates.add("Safari                                             15.1       30/11/2021 16:30:06" + NEW_LINE);
            updates.add("macOS Big Sur 11.6.1                               11.6.1     30/11/2021 16:30:49" + NEW_LINE);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (String update : updates) {
                bos.write(update.getBytes(StandardCharsets.UTF_8));
            }
            return new ByteArrayInputStream(bos.toByteArray());
        });

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        OSUpdateApplication.main(new String[] {});

        String consoleResult = os.toString();
        assertTrue(consoleResult.contains("Command Line Tools for Xcode"));
        assertTrue(consoleResult.contains("macOS Big Sur 11.6"));
        assertTrue(consoleResult.contains("Safari"));
        assertTrue(consoleResult.contains("macOS Big Sur 11.6.1"));
    }

    @Test
    public void shouldThrowsExceptionForWindowsAndLinux() {
        Mockito.when(OSQueryRunner.getOS()).thenReturn("linux");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            OSUpdateApplication.main(new String[]{});
        });
        assertEquals("Not implemented for Linux yet!!!", exception.getCause().getMessage());

        Mockito.when(OSQueryRunner.getOS()).thenReturn("windows");
        exception = Assertions.assertThrows(RuntimeException.class, () -> {
            OSUpdateApplication.main(new String[]{});
        });
        assertEquals("Not implemented for Windows yet!!!", exception.getCause().getMessage());
    }
}

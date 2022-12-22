package br.com.rodrigopostai.osupdate.os;

import br.com.rodrigopostai.osupdate.domain.OSUpdate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MacOSQueryRunnerTest {

    private static final String NEW_LINE = System.lineSeparator();

    private MockedStatic<OSQueryRunner> osQueryRunnerMockedStatic;
    private MockedStatic<ProcessStarter> processStarterMockedStatic;

    @BeforeEach
    public void setup() {
        osQueryRunnerMockedStatic = Mockito.mockStatic(OSQueryRunner.class);
        processStarterMockedStatic = Mockito.mockStatic(ProcessStarter.class);
    }

    @AfterEach
    public void tearDown() {
        osQueryRunnerMockedStatic.close();
        processStarterMockedStatic.close();
    }

    @Test
    void shouldLoadAllMacOSUpdates() throws Throwable {

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

        List<OSUpdate> updates = new MacOSQueryRunner().search();
        assertEquals(5, updates.size());
        assertEquals("Command Line Tools for Xcode", updates.get(0).getTitle());
        assertEquals("12.4", updates.get(0).getVersion());
        assertEquals("02/09/2021 17:34:13", updates.get(0).getDate());
    }

    @Test
    void shouldFailIfOSIsNotMac() {
        Mockito.when(OSQueryRunner.getOS()).thenReturn("Linux");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new MacOSQueryRunner().search();
        });
        assertEquals("OS is not Mac: Linux", exception.getMessage());
    }
}

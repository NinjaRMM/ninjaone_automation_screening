package br.com.rodrigopostai.osupdate.os;

import org.apache.commons.lang3.StringUtils;

public class OSFactory {

    private OSFactory() {

    }

    public static OSQueryRunner getInstance() {
        String os = OSQueryRunner.getOS();
        if (isMacOs(os)) {
            return new MacOSQueryRunner();
        } else if (isLinux(os)) {
            return new LinuxOSQueryRunner();
        } else if (isWindows(os)) {
            return new WindowsOSQueryRunner();
        }
        throw new RuntimeException("OS is not supported: " + os);
    }

    private static boolean isMacOs(String os) {
        return StringUtils.containsIgnoreCase(os, "Mac");
    }

    private static boolean isLinux(String os) {
        return StringUtils.containsIgnoreCase(os, "Linux");
    }

    private static boolean isWindows(String os) {
        return StringUtils.containsIgnoreCase(os, "Windows");
    }
}

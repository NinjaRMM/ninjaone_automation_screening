package br.com.rodrigopostai.osupdate.domain;

import org.apache.commons.lang3.StringUtils;

public class OSUpdate {
    private String title;
    private String version;
    private String date;

    public OSUpdate(String title, String version, String date) {
        this.title = title;
        this.version = version;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s",
                StringUtils.rightPad(title, 100),
                StringUtils.rightPad(version, 10),
                StringUtils.rightPad(date, 20));
    }
}

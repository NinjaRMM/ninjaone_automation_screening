package io.automation;


public final class PathHelper {


    private PathHelper() {
    }

    public static String getPathFile(String fileName) {
        return Environment.getInstance().getValue("baseDir") + "/" + fileName;
    }


}

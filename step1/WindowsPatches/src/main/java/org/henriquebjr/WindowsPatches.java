package org.henriquebjr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WindowsPatches {
    public static void main(String[] args) {
        Runtime rt;
        Process pr;
        BufferedReader in;
        String line = "";

        try {
            rt = Runtime.getRuntime();
            pr = rt.exec("SYSTEMINFO /FO CSV");
            in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            List<String> columns = null;

            line = in.readLine();
            columns = extractColumns(line);

            int position = -1;

            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).toLowerCase(Locale.ROOT).contains("hotfix")) {
                    position = i;
                }
            }

            line = in.readLine();
            columns = extractColumns(line);

            String hotfixColumn = columns.get(position);
            hotfixColumn = hotfixColumn.substring(hotfixColumn.indexOf(',') + 1);

            System.out.println(hotfixColumn);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    private static List<String> extractColumns(String entrada) {
        List<String> elements = new ArrayList<>();
        StringBuilder ele = null;
        boolean readingEle = false;

        for (Character c : entrada.toCharArray()) {

            if (c == '\"' && readingEle) {
                readingEle = false;
                elements.add(ele.toString());
            } else if (c == '\"') {
                readingEle = true;
                ele = new StringBuilder();
            } else if (readingEle) {
                ele.append(c);
            }
        }
        return elements;
    }
}
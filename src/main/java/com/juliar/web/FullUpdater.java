package com.juliar.web;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FullUpdater {
    public static String downloadURL = "https://api.github.com/repos/juliarLang/juliar/releases";

    public static String CheckVersion(){
        try {
            String data = readStringFromURL(downloadURL);
            int start = data.indexOf("releases/tag/Juliar-") + 20;
            return data.substring(start,start+15).replace('-','0');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String readStringFromURL(String requestURL) throws IOException
    {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}

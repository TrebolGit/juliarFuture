package com.juliar.pal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.juliar.errors.PrintError;
/**
 * platform abstraction layer.
 */
public class Primitives {
    public static String sys_file_open(String path) {
        try {
            int read = 1024;
            int N = 1024 * read;
            System.out.println("Reading file " + path);
            char[] buffer = new char[N];
            String text = "";

            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while (true) {
                read = bufferedReader.read(buffer, 0, N);
                text += new String(buffer, 0, read);

                if (read < N) {
                    break;
                }
            }

            return text;

        } catch (Exception fne) {
            new PrintError(fne.getMessage());
        }

        return "";
    }

    public static void sys_file_write(String path) {
        try{
            List<String> lines = Arrays.asList("Test Line 1", "Test Line 2");
            Path file = Paths.get("the-file-name.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            new PrintError(e.getMessage() + " sys_file_write error");
        }
    }

    public static void sys_print_line(String string) {
        System.out.println(stripQuotes(string));

    }

    public static void sys_print_int(int integer){ System.out.println(integer);}
    public static void sys_print_float(float floater){ System.out.println(floater);}
    public static void sys_print_double(double doubler){ System.out.println(doubler);}

    public static void sys_print(String string) {
        System.out.print(stripQuotes(string));
    }

    private static String stripQuotes( String s){
        if (s.startsWith("\"") && s.startsWith("\"")) {
            return (s.substring(1, s.length()-1));
        }

        return s;
    }

    public static void sys_available_memory() {
        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        System.out.print(total - free);
    }
}

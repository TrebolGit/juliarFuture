package com.juliar.errors;

import static java.lang.System.*;

/**
 * Created by AndreiM on 12/27/2016.
 */
public class JuliarLogger {
    private static boolean hasError = false;
    private static int errors = 0;


    public static boolean hasErrors(){
        return hasError;
    }

    public static void log(String msg){
        out.println(msg);
    }

    public static void log(String msg,Exception e){
        out.println(msg);
    }

    public static void log(Exception e){
        e.printStackTrace( err );
    }


    public static void logerr(String msg){
        err.println(msg);
    }

    public static void errorFound(){
        hasError = true;
        errors++;
    }

    public static int getNumberOfErrors(){
        return errors;
    }

    public static void exitIfErrors(){
        if(JuliarLogger.hasError){
            err.println("Found "+errors+" errors!");
            throw new NullPointerException();
        }
    }

    public JuliarLogger(String Message){
        out.println("Error: " + Message);
    }

    public JuliarLogger(String Message, Exception Type){
        out.println("Error: " + Message);
    }

    public static void message(String message){
        out.println( message );
    }
}
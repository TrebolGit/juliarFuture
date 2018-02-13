package com.juliar.errors;

import com.bugsnag.Bugsnag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by AndreiM on 12/27/2016.
 */
public class JuliarLogger {
    private static boolean hasError = false;
    private static int errors = 0;

    final static Logger logger = LoggerFactory.getLogger(JuliarLogger.class);
    final static Bugsnag bugsnag = new Bugsnag("c7e03c1e69143ad2fb1f3ea13ed8fda0");

    //bugsnag.addCallback(report ->report.addToTab("subsystem", "name", "2018"));
    //bugsnag.notify(new RuntimeException("Initiated"));

    public static boolean hasErrors(){
        return hasError;
    }

    public static void log(String msg){
        logger.debug(msg);
    }

    public static void log(String msg,Exception e){
        logger.debug(msg + e.toString());
    }

    public static void log(Exception e){
        logger.debug(e.toString());
    }


    public static void logerr(String msg){
        logger.debug(msg);
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
            logger.debug("Found $errors errors!");
            throw new NullPointerException();
        }
    }

    public JuliarLogger(String Message){
        logger.debug("Error: " + Message);
    }

    public JuliarLogger(String Message, Exception Type){
        logger.debug("Error: " + Message);
    }

    public static void message(String message){
        logger.debug( message );
    }
}

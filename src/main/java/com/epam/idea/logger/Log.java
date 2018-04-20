package com.epam.idea.logger;

import org.apache.log4j.Logger;

/**
 * Created by Aliaksei_Liubetski on 2015-06-02.
 */
public class Log {
    private Logger log = null;

    public Log(Class a_class){
        log = Logger.getLogger(a_class);
    }//Log

    /**
     * Logging errors by String
     * @param a_logData - String message
     */
    public void logError(String a_logData){
        log.error(a_logData);
    }

    /**
     * Logging errors by Throwable
     * @param a_exception - Throwable object
     */
    public void logError(Throwable a_exception){
        log.error(a_exception);
    }

    /**
     * Logging warning by String
     * @param a_logData - String message
     */
    public void logWarning(String a_logData){
        log.warn(a_logData);
    }

    /**
     * Logging warning by Throwable
     * @param a_exception - Throwable object
     */
    public void logWarning(Throwable a_exception){
        log.warn(a_exception);
    }

    /**
     * Logging info by String
     * @param a_logData - String message
     */
    public void log(String a_logData){
        log.info(a_logData);
    }

    /**
     * Logging debug by String
     * @param a_logData - String message
     */
    public void logDebug(String a_logData){
        log.debug(a_logData);
    }
}

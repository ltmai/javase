package com.linhmai.example.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example use of log4j2
 *
 */
public class App 
{
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args )
    {
        logger.error("Logging with lambda: {}", () -> "Hello World!");
    }
}

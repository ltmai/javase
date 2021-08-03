package com.linhmai.example.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Example use of log4j2
 *
 */
public class App 
{
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args )
    {
        ThreadContext.push("Thread-" + Thread.currentThread().getId());
        logger.error("Start processing");
        ThreadContext.push("TXID=1234");
        logger.error("Process transaction");
        ThreadContext.pop();
        logger.error("Stop processing");
        ThreadContext.clearAll();
    }
}

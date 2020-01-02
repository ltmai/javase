package com.lini.utilities;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Set of static utility functions
 */
public class Utils
{
    /**
     * Get current timestamp
     * 
     * @return Current timestamp
     */
    public static Timestamp getCurrentTime()
    {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * Unroll a throwable to its intial cause
     * 
     * @param t
     * @return Intial cause
     */
    public static Throwable getInitialCause(Throwable t)
    {
        while ( t.getCause() != null )
        {
            t = t.getCause();
        }
        return t;
    }

    /**
     * Return the initial cause message of a Throwable
     * 
     * @param t
     * @return Initial cause message
     */
    public static String getInitialCauseMessage(Throwable t)
    {
        return getInitialCause(t).getMessage();
    }

    /**
     * Unroll the input Throwable object until causing exception of type cls is
     * found.
     * 
     * @param t
     *        Input Throwable object
     * @param cls
     *        Expected exception class
     * @return The causing exception of expected type if found or null
     *         otherwise.
     */
    public static Throwable getCauseByClass(Throwable t, Class<? extends Throwable> cls)
    {
        while ( t != null )
        {
            if ( cls.isInstance(t) )
            {
                return t;
            }
            t = t.getCause();
        }
        return t;
    }
}


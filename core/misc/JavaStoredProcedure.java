//package lini.com.db.oracle.plsql;

public class JavaStoredProcedure {
    public static String say(String msg) {
        return "Oracle version: " + System.getProperty("oracle.jserver.version") + System.getProperty("line.separator") + "Hello, " + msg;
    }
}
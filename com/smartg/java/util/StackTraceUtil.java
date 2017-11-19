package com.smartg.java.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StackTraceUtil {


    private static final boolean traceMethodCalls = true;

    public static String getStackTraceLine() {
        return getStackTraceLine(1);
    }

    private static String getStackTraceLine(final int line) {
        return new Exception().getStackTrace()[line].toString();
    }

    public static String getStackTraceLine(Throwable t) {
        return t.getStackTrace()[0].toString();
    }

    public static void trace() {
        trace(2, 1);
    }

    public static void trace(int first) {
        trace(first, 1);
    }

    public static void trace(int first, int n) {
        if (traceMethodCalls) {
            final Exception exception = new Exception();
            StackTraceElement[] stackTrace = exception.getStackTrace();
            for (int i = 0; i < n; i++) {
                int index = first + i;
                if (index < stackTrace.length) {
                    String s = stackTrace[index].toString();
                    getLogger().log(Level.INFO, s);
                }
            }
            getLogger().log(Level.INFO, "--------------------------");
        }
    }

    public static void severe(Throwable ex) {
        log(Level.SEVERE, ex);
    }
    
    public static void warning(Throwable ex) {
        log(Level.WARNING, ex);
    }
    
    public static void warning(String message) {
        log(3, Level.WARNING, message);
    }

    public static void log(Level level, String message, Throwable ex) {
        getLogger().log(level, " {0} at {1}", new Object[]{message, StackTraceUtil.getStackTraceLine(ex)});
    }

	private static Logger getLogger() {
		return Logger.getGlobal();
	}

    public static void log(Level level, Throwable ex) {
        getLogger().log(level, " {0} at {1}", new Object[]{ex.getMessage(), StackTraceUtil.getStackTraceLine(ex)});
    }

    public static void log(Level level, String message) {
    	log(2, level, message);
    }
    
    public static void log(int line, Level level, String message) {
        getLogger().log(level, message + " at {0}", StackTraceUtil.getStackTraceLine(line));
    }


    public static void log(Level level, String message, int lineCount) {
        for (int line = 0; line < lineCount; line++) {
            getLogger().log(level, message + " at {0}", StackTraceUtil.getStackTraceLine(2 + line));
        }
    }

    public static void log(Level level, String message, Object param) {
        getLogger().log(level, message + " {0} at {1}", new Object[]{param, StackTraceUtil.getStackTraceLine(2)});
    }

}

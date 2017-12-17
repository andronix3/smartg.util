package com.smartg.java.util;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StackTraceUtil {

    private static final boolean traceMethodCalls = true;

    public static String getStackTraceLine() {
        return getStackTraceLine(0);
    }

    private static String getStackTraceLine(final int line) {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        return stackTrace[getFirstLine(stackTrace) + line].toString();
    }

    public static String getStackTraceLine(Throwable t) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        return stackTrace[getFirstLine(stackTrace)].toString();
    }

    public static String getStackTraceLine(Throwable t, int count) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = t.getStackTrace();
        int firstLine = getFirstLine(stackTrace);
        for (int i = 0; i < count; i++) {
            int n = firstLine + i;
            if (n < stackTrace.length) {
                sb.append(stackTrace[n].toString());
                sb.append("\n");
            }
        }
        return sb.toString();
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
            int firstLine = getFirstLine(stackTrace);
            for (int i = 0; i < n; i++) {
                int index = firstLine + first + i;
                if (index < stackTrace.length) {
                    String s = stackTrace[index].toString();
                    Logger.getLogger("Labworks").log(Level.INFO, s);
                }
            }
            Logger.getLogger("Labworks").log(Level.INFO, "--------------------------");
        }
    }

    public static void severe(Throwable ex) {
        log(Level.SEVERE, ex, 5);
    }

    public static void severe(Throwable ex, int count) {
        log(Level.SEVERE, ex, count);
    }

    public static void warning(Throwable ex) {
        log(Level.WARNING, ex);
    }

    public static void warning(String message) {
        log(Level.WARNING, message);
    }

    public static void log(Level level, String message, Throwable ex) {
        Logger.getLogger("Labworks").log(level, " {0} at {1}", new Object[]{message, StackTraceUtil.getStackTraceLine(ex)});
    }

    public static void log(Level level, String message, Throwable ex, int lineCount) {
        Logger.getLogger("Labworks").log(level, " {0} at {1}", new Object[]{message, StackTraceUtil.getStackTraceLine(ex, 5)});
    }

    public static void log(Level level, Throwable ex) {
        Logger.getLogger("Labworks").log(level, " {0} at {1}", new Object[]{ex.getMessage(), StackTraceUtil.getStackTraceLine(ex)});
    }

    public static void log(Level level, Throwable ex, int lineCount) {
        Logger.getLogger("Labworks").log(level, " {0} at {1}", new Object[]{ex.getMessage(), StackTraceUtil.getStackTraceLine(ex, lineCount)});
    }

    public static void log(Level level, String message) {
        Logger.getLogger("Labworks").log(level, message + " at {0}", StackTraceUtil.getStackTraceLine());
    }

    public static void log(Level level, String message, int lineCount) {
        for (int line = 1; line <= lineCount; line++) {
            Logger.getLogger("Labworks").log(level, message + " at {0}", StackTraceUtil.getStackTraceLine(line));
        }
    }

    public static void log(Level level, String message, Object param) {
        Logger.getLogger("Labworks").log(level, message + " {0} at {1}", new Object[]{param, StackTraceUtil.getStackTraceLine()});
    }

    private static int getFirstLine() {
        return getFirstLine(new Exception().getStackTrace());
    }

    private static int getFirstLine(StackTraceElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].getClassName().equalsIgnoreCase(StackTraceUtil.class.getName())) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public static <T> T measureTime(Supplier<T> s, String message) {
        long t = System.currentTimeMillis();
        T get = s.get();
        log(Level.INFO, message + ": " + (System.currentTimeMillis() - t) + "ms");
        return get;
    }
}

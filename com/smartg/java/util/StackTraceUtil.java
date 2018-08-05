package com.smartg.java.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StackTraceUtil {

    private static final boolean traceMethodCalls = true;
    private static Set<String> stackSet = new HashSet<>();

    private static boolean filterIdenticalMessages;

    public static boolean isFilterIdenticalMessages() {
        return filterIdenticalMessages;
    }

    public static void setFilterIdenticalMessages(boolean filterIdenticalMessages) {
        StackTraceUtil.filterIdenticalMessages = filterIdenticalMessages;
    }

    public static String getStackTraceLine() {
        return getStackTraceLine(0);
    }

    private static String getStackTraceLine(final int line) {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        return stackTrace[getFirstLine(stackTrace) + line].toString();
    }

    public static String getStackTraceLine(StackTraceElement[] stackTrace, int count, int firstLine) {
        StringBuilder sb = new StringBuilder();
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
            Builder builder = new Builder();
            FST_Exception ex = new FST_Exception(first, first + n, new Throwable());
            builder.getLogger().log(Level.INFO, ex.toString());
            builder.getLogger().log(Level.INFO, "--------------------------");
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

    public static void warning(String message, int lineCount) {
        log(Level.WARNING, message, lineCount);
    }

    public static void log(Level level, String message, Throwable ex) {
        Builder builder = new Builder().setException(ex);
        builder.log(level, message + " at {0}", new Object[]{builder.toString()});
    }

    public static void log(Level level, String message, Throwable ex, int lineCount) {
        Builder builder = new Builder().setException(ex).setLineCount(lineCount);
        builder.log(level, message + " at {0}", new Object[]{builder.toString()});
    }

    public static void log(Level level, Throwable ex) {
        Builder builder = new Builder().setException(ex);
        builder.log(level, ex.getMessage() + " at {0}", new Object[]{builder.toString()});
    }

    public static void log(Level level, Throwable ex, int lineCount) {
        Builder builder = new Builder().setException(ex).setLineCount(lineCount);
        builder.log(level, ex.getMessage() + " at {0}", new Object[]{builder.toString()});
    }

    public static void log(Level level, String message) {
        Builder builder = new Builder();
        builder.log(level, message);
    }

    public static void log(Level level, String message, int lineCount) {
        Builder builder = new Builder().setLineCount(lineCount);
        builder.log(level, message);
    }

    private static boolean isUnique(String message) {
        return stackSet.add(message);
    }

    public static void log(Level level, String message, Object param) {
        Builder builder = new Builder();
        builder.log(level, message + " {0} at {1}", new Object[]{param, builder.toString()});
    }

    static class Builder {

        int lineCount = 1;
        private Throwable exception;
        private boolean unique = filterIdenticalMessages;

        private StackTraceElement[] elements;
        private String className;
        private Integer firstLine;

        public int getLineCount() {
            return lineCount;
        }

        public Builder setLineCount(int lineCount) {
            this.lineCount = lineCount;
            return this;
        }

        private void checkFirstLine() {
            checkElements();
            if (firstLine == null) {
                firstLine = StackTraceUtil.getFirstLine(elements);
            }
        }

        private void checkElements() {
            if (elements == null) {
                elements = getException().getStackTrace();
            }
        }

        public void setUnique(boolean unique) {
            this.unique = unique;
        }

        public int getFirstLine() {
            return firstLine;
        }

        public Throwable getException() {
            if (exception == null) {
                exception = new Throwable();
            }
            return exception;
        }

        public Builder setException(Throwable exception) {
            this.exception = exception;
            return this;
        }

        public String getClassName() {
            return className;
        }

        public Logger getLogger() {
            this.elements = getException().getStackTrace();
            this.firstLine = StackTraceUtil.getFirstLine(this.elements);
            // this.stackTraceLine = StackTraceUtil.getStackTraceLine(elements, lineCount,
            // firstLine);
            return Logger.getLogger(this.elements[firstLine].getClassName());
        }

        @Override
        public String toString() {
            checkFirstLine();
            checkElements();
            StringBuilder sb = new StringBuilder();
            int max = Math.min(firstLine + lineCount + 1, elements.length);
            for (int i = firstLine; i < max; i++) {
                sb.append(elements[i].toString());
                sb.append("\n");
            }
            return sb.toString();
        }

        public void log(Level level) {
            String line = getStackTraceLine();
            if (!unique || StackTraceUtil.isUnique(line)) {
                getLogger().log(level, line);
            }
        }

        public void log(Level level, String msg) {
            String message = msg + " at \n" + toString();
            if (!unique || StackTraceUtil.isUnique(message)) {
                getLogger().log(level, message);
            }
        }

        public void log2(Level level, String msg) {
            String message = toString();
            if (!unique || StackTraceUtil.isUnique(msg + message)) {
                getLogger().log(level, msg + " at {0}", message);
            }
        }

        public void log(Level level, String msg, Object [] params) {
            String line = getStackTraceLine();
            if (!unique || StackTraceUtil.isUnique(line)) {
                getLogger().log(level, msg + " {0} at {1} ", new Object[]{params, toString()});
            }
        }
    }

    private static int getFirstLine(StackTraceElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            String className = elements[i].getClassName();
            if (className.equalsIgnoreCase(StackTraceUtil.class.getName())
                    || className.equalsIgnoreCase(Builder.class.getName())) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public static <T> T measureTime(Supplier<T> s, String message) {
        return measureTime(s, message, 0);
    }

    public static <T> T measureTime(Supplier<T> s, String message, long interval) {
        long t = System.currentTimeMillis();
        T get = s.get();
        long m = System.currentTimeMillis() - t;
        if (m > interval) {
            log(Level.INFO, message + ": " + m + "ms");
        }
        return get;
    }

    public static void measureTime(Runnable r, String message) {
        measureTime(r, message, 0);
    }

    public static void measureTime(Runnable r, String message, long interval) {
        long t = System.currentTimeMillis();
        r.run();
        long m = System.currentTimeMillis() - t;
        if (m > interval) {
            log(Level.INFO, message + ": " + m + "ms");
        }
    }
}

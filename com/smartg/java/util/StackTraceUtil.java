package com.smartg.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackTraceUtil {

    private static final boolean traceMethodCalls = true;
    private static final Set<String> stackSet = new HashSet<>();

    private static boolean filterIdenticalMessages;
    private static final String[] ignoredPackages = {"com.sun", "com.oracle", "java.lang", "javax", "java.applet",
        "java.awt", "java.beans", "java.io", "java.net", "java.math", "java.nio", "java.rmi", "java.security",
        "java.sql", "java.text", "java.time", "java.util", "jdk.internal", "jdk.management", "jdk.net", "org.ietf",
        "org.jcp", "org.omg", "org.w3c", "org.xml", "sun.applet", "sun.awt", "sun.audio", "sun.corba", "sun.dc",
        "sun.font", "sun.invoke", "sun.io", "sun.java2d", "sun.launcher", "sun.management", "sun.misc", "sun.net",
        "sun.nio", "sun.print", "sun.reflect", "sun.rmi", "sun.security", "sun.swing", "sun.text", "sun.tools",
        "sun.tracing", "sun.util", "javafx.event", "javafx.beans", "javafx.scene", "com.sybase"
    };

    private static List<String> ignoredList = new ArrayList<>(Arrays.asList(ignoredPackages));

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

    public static void addIgnoredPackage(String pkg) {
        ignoredList.add(pkg);
    }

    public static void removeIgnoredPackage(String pkg) {
        ignoredList.remove(pkg);
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

    public static void warningIfNull(Object obj, String message) {
        if (obj == null) {
            log(Level.WARNING, message);
        }
    }

    public static void warningIfNotNull(Object obj, String message) {
        if (obj != null) {
            log(Level.WARNING, message);
        }
    }

    public static void warning(String message, int lineCount) {
        log(Level.WARNING, message, lineCount);
    }

    public static void warningIfNull(Object obj, String message, int lineCount) {
        if (obj == null) {
            log(Level.WARNING, message, lineCount);
        }
    }

    public static void warningIfNotNull(Object obj, String message, int lineCount) {
        if (obj != null) {
            log(Level.WARNING, message, lineCount);
        }
    }

    public static void log(Level level, String message, Throwable ex) {
        Builder builder = new Builder().setException(ex);
        builder.log(level, message, new Object[]{ex.getMessage()});
    }

    public static void log(Level level, String message, Throwable ex, int lineCount) {
        Builder builder = new Builder().setException(ex).setLineCount(lineCount);
        builder.log(level, message, new Object[]{ex.getMessage()});
    }

    public static void log(Level level, Throwable ex) {
        Builder builder = new Builder().setException(ex);
        builder.log(level, "", new Object[]{ex.getMessage()});
    }

    public static void log(Level level, Throwable ex, int lineCount) {
        Builder builder = new Builder().setException(ex).setLineCount(lineCount);
        builder.log(level, ex.getMessage());
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
        builder.log(level, " {0} at {1}", new Object[]{message, param});
    }

    public static String toString(Throwable t) {
        Builder builder = new Builder();
        builder.setException(t);
        StackTraceElement[] elements = builder.getElements();
        return Stream.of(elements).map(e -> e.toString()).collect(Collectors.joining("\n"));
    }

    static class Builder {

        int lineCount = 1;
        private Throwable exception;
        private boolean unique = filterIdenticalMessages;

        private StackTraceElement[] elements;
        private String className;
        private Integer firstLine;

        public Builder() {
            className = "";
        }

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

        private boolean filterOut(String s) {
            for (String p : ignoredList) {
                if (s.startsWith(p)) {
                    return true;
                }
            }
            return false;
        }

        private StackTraceElement[] getElements() {
            StackTraceElement[] elems = getException().getStackTrace();
            List<StackTraceElement> collected = new ArrayList<>();
            for (StackTraceElement elem : elems) {
                if (!filterOut(elem.getClassName())) {
                    collected.add(elem);
                }
            }
            return collected.toArray(new StackTraceElement[0]);
        }

        private void checkElements() {
            if (elements == null) {
                elements = getElements();
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
                exception = new IgnoredException();
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
            this.elements = getElements();
            this.firstLine = StackTraceUtil.getFirstLine(this.elements);
            // this.stackTraceLine = StackTraceUtil.getStackTraceLine(elements, lineCount,
            // firstLine);
            try {
                return Logger.getLogger(this.elements[firstLine].getClassName());
            } catch (Throwable t) {
                return Logger.getGlobal();
            }
        }

        @Override
        public String toString() {
            checkElements();
            checkFirstLine();
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
            if (msg == null) {
                msg = "";
            }
            if (!(getException() instanceof IgnoredException)) {
                msg = getException().getClass().getName() + "\n" + msg;
            }

            if (!unique || StackTraceUtil.isUnique(msg + message)) {
                getLogger().log(level, msg + " at {0}", message);
            }
        }

        public void log(Level level, String msg, Object[] params) {
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
        log(Level.WARNING, "Staring " + message);
        long t = System.currentTimeMillis();
        T get = s.get();
        long m = System.currentTimeMillis() - t;
        if (m > interval) {
            log(Level.WARNING, message + ": " + m + "ms");
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

    static class IgnoredException extends Exception {

    }
}

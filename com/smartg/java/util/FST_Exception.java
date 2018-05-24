package com.smartg.java.util;

import java.util.Arrays;

public class FST_Exception extends Throwable {

    private final int firstLine;
    private final int lastLine;

    private final Throwable cause;
    private final String message;

    public FST_Exception(int firstLine, int lastLine, String message) {
        this.cause = new Throwable(message);
        this.message = message;
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        updateStackTrace();
    }

    public FST_Exception(int firstLine, int lastLine, String message, Throwable cause) {
        this.cause = cause;
        this.message = message;
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        updateStackTrace();
    }

    public FST_Exception(int firstLine, int lastLine, Throwable cause) {
        this.cause = cause;
        this.message = cause.getMessage();
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        updateStackTrace();
    }

    private void updateStackTrace() {
        StackTraceElement[] stackTrace = this.cause.getStackTrace();
        int max = Math.min(lastLine, stackTrace.length);
        if (firstLine < max) {
            StackTraceElement[] copyOfRange = Arrays.copyOfRange(stackTrace, firstLine, max);
            setStackTrace(copyOfRange);
        }
    }

    @Override
    public String toString() {
        StackTraceElement[] stackTrace = getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement e : stackTrace) {
            sb.append(e.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}

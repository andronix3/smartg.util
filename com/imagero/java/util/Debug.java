/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.imagero.java.util;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 28.01.2009
 * 
 * @author Andrey Kuznetsov
 * @deprecated
 */
public class Debug {

    static Logger l = Logger.getLogger("com.imagero.java.util.Debug");
    static {
	l.setLevel(Level.FINER);
    }

    public static final boolean DEBUG = false;
    public static final boolean DUMP = false;

    public static final boolean REPORT_ERRORS = true;
    public static final boolean REPORT_MESSAGES = false;

    public static void print(String sourceClass, String sourceMethod, Throwable t) {
	if (DEBUG) {
	    l.throwing(sourceClass, sourceMethod, t);
	}
    }

    public static void print(Throwable t, PrintStream out) {
	if (DEBUG) {
	    t.printStackTrace(out);
	}
    }

    public static void print(String s, PrintStream out) {
	if (DEBUG) {
	    out.print(s);
	}
    }

    public static void print(int i, PrintStream out) {
	if (DEBUG) {
	    out.print(i);
	}
    }

    public static void print(String s) {
	if (DEBUG) {
	    l.info(s);
	}
    }

    public static void print(Object s) {
	if (DEBUG) {
	    l.info(String.valueOf(s));
	}
    }

    public static void println(String s) {
	if (DEBUG) {
	    Logger l = Logger.getLogger("com.imagero.java.util");
	    l.info(s + "\n");
	}
    }

    public static void println(Object s) {
	if (DEBUG) {
	    Logger l = Logger.getLogger("com.imagero.java.util");
	    l.info(String.valueOf(s) + "\n");
	}
    }

    public static void println(int i, PrintStream out) {
	if (DEBUG) {
	    out.println(i);
	}
    }

    public static void println(String s, PrintStream out) {
	if (DEBUG) {
	    out.println(s);
	}
    }

    public static void error(String sourceClass, String sourceMethod, Throwable t) {
	if (REPORT_ERRORS) {
	    l.throwing(sourceClass, sourceMethod, t);
	}
    }

    public static void error(Throwable t) {
	error("", "", t);
    }

    public static void message(String sourceClass, String sourceMethod, Throwable t) {
	if (REPORT_MESSAGES) {
	    l.throwing(sourceClass, sourceMethod, t);
	}
    }

    public static void error(String s) {
	if (REPORT_ERRORS) {
	    println(s);
	}
    }

    public static void message(String s) {
	if (REPORT_MESSAGES) {
	    println(s);
	}
    }

    public static void printHexArray(byte[] b) {
	if (DEBUG) {
	    int length = b.length / 16;
	    int k;
	    for (int i = 0; i < length; i++) {
		int index = i * 16;
		for (int j = 0; j < 8; j++) {
		    k = index + j;
		    Debug.print(ensureLength(Integer.toHexString(b[k] & 0xFF), 2));
		    Debug.print(" ");
		}
		Debug.print(" ");
		for (int j = 0; j < 8; j++) {
		    k = index + 8 + j;
		    Debug.print(ensureLength(Integer.toHexString(b[k] & 0xFF), 2));
		    Debug.print(" ");
		}
		Debug.print("\n");
	    }
	}
    }

    public static void printHexArray2(byte[] b) {
	if (DEBUG) {
	    int length = b.length;
	    for (int i = 0; i < length; i++) {
		if (i > 0 && i % 16 == 0) {
		    Debug.println("\n");
		}
		Debug.print(ensureLength(Integer.toHexString(b[i] & 0xFF), 2));
		Debug.print(" ");
	    }
	}
    }

    static String ensureLength(String s, int length) {
	while (s.length() < length) {
	    s = "0" + s;
	}
	return s;
    }
}

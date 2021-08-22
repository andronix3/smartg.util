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

package com.smartg.logr;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility to read (possibly continuously changing) log files.
 * 
 * @author andrey
 * 
 */
@SuppressWarnings("unused")
public class LogFileReader {

	enum ExitCause {
		USER, EXCEPTION, RAFISNULL, FDINVALID, AUTOFINISH
	}

	enum FileState {
		OK, TOO_SHORT, INVALID, NULL
	}

	// -----------------------------private fields-----------------------------

	private final File file;
	private RandomAccessFile raf;
	private volatile boolean finished;
	private long lastSize;
	private final boolean autoFinish;
	private boolean skipExistingLines;
	private boolean blocked;

	private final Set<LogFileReaderListener> listeners = new HashSet<>();

	private ExitCause exitCause;

	private Timer t;

	private final String exitMessage;
	private long lastOffset;
	private Throwable exception;

	private FileState lastState;

	// -----------------------------constructor-----------------------------

	@SuppressWarnings("unused")
	public LogFileReader(String filename, String exitMessage) {
		this(filename, exitMessage, false, false);
	}

	public LogFileReader(String filename, String message, boolean autoFinish, boolean skipExistingLines) {
		this.file = new File(filename);
		this.exitMessage = message;
		this.autoFinish = autoFinish;
		this.skipExistingLines = skipExistingLines;
	}

	// -----------------------------private methods-----------------------------

	private void sendMessage(String s) {
		LogFileReaderEvent e = new LogFileReaderEvent(this, s);
		for (LogFileReaderListener l : listeners) {
			l.lineRead(e);
		}
	}

	@SuppressWarnings("unused")
	private boolean checkFileSize() {
		if (file.length() != lastSize) {
			lastSize = file.length();
			return true;
		}
		return false;
	}

	private void read0() {
		exitCause = null;
		FileState fs = null;
		try {
			mayBeCreateRAF();
			while ((fs = checkRAF()) == FileState.OK) {
				if (raf.length() > raf.getFilePointer()) {
					if (!readLine()) {
						break;
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			exception = t;
			raf = null;
			exitCause = ExitCause.EXCEPTION;
		} finally {
			lastState = fs;
			if (autoFinish) {
				finished = true;
				if (exitCause == null) {
					exitCause = ExitCause.AUTOFINISH;
				}
			}
			sendMessage(exitMessage);
		}
	}

	private FileState checkRAF() throws IOException {
		FileState fs = FileState.OK;
		if (raf == null) {
			fs = FileState.NULL;
		} else if (!raf.getFD().valid()) {
			fs = FileState.INVALID;
		} else if (lastOffset >= raf.length()) {
			fs = FileState.TOO_SHORT;
		}
		return fs;
	}

	private boolean readLine() throws IOException {
		blocked = true;
		String line = raf.readLine();
		blocked = false;
		lastOffset = raf.getFilePointer();
		if (line == null) {
			return false;
		}
		sendMessage(line);
		return true;
	}

	private void mayBeCreateRAF() throws IOException {
		FileState checkRAF = checkRAF();
		switch (checkRAF) {
			case INVALID:
			case NULL:
				raf = new RandomAccessFile(file, "r");
				if (lastOffset == 0) { // first time
					if (skipExistingLines) {
						skipExistingLines = false;
						raf.seek(raf.length());
					}
				} else {
					if (lastOffset > raf.length()) {
						lastOffset = raf.length();
					}
					raf.seek(lastOffset);
				}
				lastOffset = raf.getFilePointer();
				break;
			case TOO_SHORT:
				raf.seek(lastOffset);
				lastOffset = raf.getFilePointer();
				break;
			default:
				break;
		}
	}

	private void close() {
		if (t != null) {
			t.stop();
			t = null;
		}
		if (raf != null) {
			try {
				raf.close();
			} catch (IOException ex) {
				// ignore
			}
			raf = null;
		}
	}

	// -----------------------------public methods-----------------------------

	@SuppressWarnings("unused")
	public File getFile() {
		return file;
	}

	@SuppressWarnings("unused")
	public int getDelay() {
		return t.getDelay();
	}

	@SuppressWarnings("unused")
	public void setDelay(int delay) {
		t.setDelay(delay);
	}

	@SuppressWarnings("unused")
	public Throwable getException() {
		return exception;
	}

	/**
	 * read file asynchronously
	 */
	@SuppressWarnings("unused")
	public void read() {
		start();
	}

	@SuppressWarnings("unused")
	public void readAll() {
		while (!finished) {
			read0();
		}
	}

	@SuppressWarnings("unused")
	public long getLasModified() {
		return file.lastModified();
	}

	public void start() {
		finished = false;
		t = new Timer(100, e -> {
			// if (checkFileSize()) {
			read0();
			// if (!finished) {
			// t.restart();
			// }
			// }
		});
		t.setInitialDelay(10);
		t.setRepeats(true);
		t.start();
	}

	public void exitRun() {
		finished = true;
		exitCause = ExitCause.USER;
		close();
	}

	@SuppressWarnings("unused")
	public void restart() {
		exitRun();
		exitCause = null;
		finished = false;
		start();
	}

	@SuppressWarnings("unused")
	public boolean isFinished() {
		return finished;
	}

	@SuppressWarnings("unused")
	public void addLogFileReaderListener(@NotNull LogFileReaderListener l) {
		listeners.add(l);
	}

	public void removeLogFileReaderListener(@NotNull LogFileReaderListener l) {
		listeners.remove(l);
	}

	@Override
	public String toString() {
		String name = file.getName();
		String message = "";
		if (exception != null) {
			message = exception.getMessage();
		}
		return "LogReader [ (" + name + ") finished=" + finished + ", autoFinish=" + autoFinish + ", exitCause=" + exitCause + ", exitMessage=" + exitMessage
				+ ", exception=" + message + ", lastState=" + lastState + " blocked=" + blocked + "]";
	}
}

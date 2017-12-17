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
package com.smartg.java.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import com.imagero.java.event.EventSource;
import com.imagero.java.event.VEvent;
import com.imagero.java.event.VEventListener;

/**
 * ThreadManager helps to distribute tasks to threads (runners). It is possible
 * to pause/continue work.
 *
 * @author Andrey Kuznetsov
 */
public class ThreadManager extends EventSource implements Executor {

	private static final boolean debug = false;

	/**
	 * runner count
	 */
	private volatile int count;

	private int total;

	/**
	 * Threads
	 */
	private Runner[] runners;

	/**
	 * Running tasks
	 */
	private RunnablePlus[] current;

	/**
	 * job queue
	 */
	private ArrayList<RunnablePlus> tasks = new ArrayList<>();

	/**
	 * Create ThreadManager with one runner
	 */
	public ThreadManager() {
		this(1);
	}

	/**
	 * create ThreadManager with specified amount of runners
	 *
	 * @param count
	 *            amount of runners
	 */
	public ThreadManager(int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("" + count);
		}

		this.runners = new Runner[count];
		this.current = new RunnablePlus[count];
		for (int i = 0; i < runners.length; i++) {
			runners[i] = new Runner();
			runners[i].setName(" " + i);
			runners[i].start();
		}
	}

	/**
	 * remove all tasks from queue
	 */
	public void clearTasks() {
		this.tasks.clear();
	}

	/**
	 * add task to task queue
	 *
	 * @param r
	 *            Runnable
	 */
	private void addTask(Runnable r) {
		addTask(r, "" + total++);
	}

	private void addTask(Runnable r, String name) {
		int index = this.tasks.indexOf(r);
		if (index < 0 && !isCurrentImpl(r)) {
			this.tasks.add(new RunnablePlusImpl(name, r));
			wakeUp();
		}
	}

	/**
	 * add or move task to the head of queue
	 *
	 * @param r
	 *            Runnable
	 */
	public void executeFast(Runnable r) {
		int index = this.tasks.indexOf(r);
		if (index < 0) {
			if (!isCurrentImpl(r)) {
				this.tasks.add(0, new RunnablePlusImpl("" + total++, r));
				wakeUp();
			}
		} else {
			this.tasks.remove(r);
			this.tasks.add(0, (RunnablePlus) r);
			wakeUp();
		}
	}

	/**
	 * add multiple tasks to queue
	 *
	 * @param tasks
	 */
	public void add(Enumeration<Runnable> tasks) {
		add(tasks, "" + total++);
	}
	
	public void add(Enumeration<Runnable> tasks, String name) {
		RunnableEnumeration r = new RunnableEnumeration(tasks, name);
		int index = this.tasks.indexOf(r);
		if (index < 0 && !isCurrentImpl(r)) {
			this.tasks.add(r);
			wakeUp();
		}
	}


	private boolean isCurrentImpl(Runnable r) {

		for (Runnable element : current) {
			if (r.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if specified task (job) is now running (in progress)
	 *
	 * @param r
	 *            Runnable task to check
	 * @return true if job is in progress
	 */
	public boolean isCurrent(Runnable r) {
		return isCurrentImpl(r);
	}

	/**
	 * check if this task is already in queue
	 *
	 * @param r
	 *            Runnable task to check
	 * @return true if specified job is alredy in job queue or is in progress
	 *         (running)
	 */
	public boolean hasTask(Runnable r) {
		return isCurrentImpl(r) || this.tasks.contains(r);
	}

	/**
	 * wake up all runners
	 */
	protected synchronized void wakeUp() {
		notifyAll();
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
	}

	public void destroy() {
		for (Runner runner : runners) {
			runner.stopMe();
		}
		wakeUp();
		// for (int i = 0; i < runners.length; i++) {
		// runners[i] = null;
		// }
		// runners = null;
	}

	public void setPaused(boolean p) {
		for (Runner runner : runners) {
			runner.setPaused(p);
		}
		if (!p) {
			wakeUp();
		}
	}

	/**
	 * If paused - let each Thread make next job and pause again, noop
	 * otherwise.
	 */
	public void doNext() {
		if (isPaused()) {
			for (Runner runner : runners) {
				runner.doNext();
			}
			wakeUp();
		}
	}

	public boolean isPaused() {
		return runners[0].paused;
	}

	/**
	 * get next job from queue
	 *
	 * @return next task
	 */
	protected synchronized RunnablePlus nextTask() {
		return next();
	}

	private RunnablePlus next() {
		if (this.tasks.size() > 0) {
			RunnablePlus r = tasks.get(0);
			if (r instanceof RunnableEnumeration) {
				RunnableEnumeration runen = (RunnableEnumeration) r;
				if (runen.hasMoreElements()) {
					return runen.nextElement();
				}
				tasks.remove(0);
				post(new TM_Event(this, runen, TM_Event.Type.EnumerationExited));
				return next();
			}
			tasks.remove(0);
			return r;
		}
		post(new TM_Event(this));// send QueueEmpty
		return null;
	}

	synchronized void doWait() {
		try {
			this.wait();
		} catch (InterruptedException ex) {
		}
	}

	private static final class RunnableEnumeration implements RunnablePlus, Enumeration<Runnable> {

		private final Enumeration<Runnable> en;
		private final String name;
		private int count;

		public RunnableEnumeration(Enumeration<Runnable> en, String name) {
			this.en = en;
			this.name = name;
		}

		@Override
		public void run() {
			// should never be called
		}

		@Override
		public boolean hasMoreElements() {
			return en.hasMoreElements();
		}

		@Override
		public RunnablePlus nextElement() {
			return new RunnablePlusImpl(name + "#" + count++, en.nextElement());
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj instanceof RunnableEnumeration) {
				RunnableEnumeration runen = (RunnableEnumeration) obj;
				return runen.en.equals(en);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return getClass().getName().hashCode() * en.hashCode();
		}

		@Override
		public String getName() {
			return null;
		}
	}

	public int getRunningTaskCount() {
		int cnt = 0;
		for (Runnable element : current) {
			if (element != null) {
				cnt++;
			}
		}
		return cnt;
	}

	public static class TM_Event extends VEvent {

		public static enum Type {
			RunnableStarted, RunnableExited, EnumerationStarted, EnumerationExited, QueueEmpty
		}

		public final Type type;

		public TM_Event(EventSource source) {
			super(source);
			this.type = Type.QueueEmpty;
		}

		public TM_Event(EventSource source, Runnable command, Type type) {
			super(source, command);
			this.type = type;
		}
	}

	static interface RunnablePlus extends Runnable {
		public String getName();
	}

	static class RunnablePlusImpl implements RunnablePlus {
		private String name;
		private Runnable runnable;

		public RunnablePlusImpl(String name, Runnable runnable) {
			this.name = name;
			this.runnable = runnable;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void run() {
			runnable.run();
		}

	}

	public static class TM_EventListener extends VEventListener {

		public TM_EventListener() {
			super(TM_Event.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void call(VEvent e) {
			TM_Event tm = (TM_Event) e;
			switch (tm.type) {
			case EnumerationExited:
				enumerationExited((Enumeration<Runnable>) tm.getCommand());
				break;
			case EnumerationStarted:
				enumerationStarted((Enumeration<Runnable>) tm.getCommand());
				break;
			case QueueEmpty:
				queueEmpty();
				break;
			case RunnableExited:
				runnableExited((Runnable) tm.getCommand());
				break;
			case RunnableStarted:
				runnableStarted((Runnable) tm.getCommand());
				break;
			}
		}

		/**
		 * @param e
		 */
		public void enumerationExited(Enumeration<Runnable> e) {
		}

		/**
		 * @param e
		 */
		public void enumerationStarted(Enumeration<Runnable> e) {
		}

		/**
		 * @param r
		 */
		public void runnableStarted(Runnable r) {
		}

		/**
		 * @param r
		 */
		public void runnableExited(Runnable r) {
		}

		public void queueEmpty() {
		}
	}

	class Runner extends Thread {

		protected int num;
		volatile boolean stopped;
		volatile boolean paused;
		volatile boolean doNext;

		Runner() {
			num = count++;
			setDaemon(true);
		}

		void stopMe() {
			stopped = true;
		}

		void setPaused(boolean p) {
			paused = p;
		}

		void doNext() {
			doNext = true;
		}

		boolean isStopped() {
			return stopped;
		}

		@Override
		public void run() {
			while (!isStopped()) {
				if (!paused || doNext) {
					doNext = false;
					current[num] = nextTask();
					if (current[num] != null) {
						try {
							current[num].run();
							current[num] = null;
						} catch (Throwable ex) {
							current[num] = null;
							StackTraceUtil.warning(ex);
						}
					} else {
						doWait();
					}
				} else {
					doWait();
				}
			}
			if (debug) {
				Logger logger = Logger.getLogger("com.smartg.java.util");
				logger.info("Runner exited");
			}
		}
	}

	@Override
	public void execute(Runnable command) {
		addTask(command);
	}

	public void execute(Runnable command, String name) {
		addTask(command, name);
	}
}

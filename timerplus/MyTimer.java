package timerplus;

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.smartg.java.util.SafeIterator;

import timerplus.MyTimer.ActionList.ActionIterator;

public class MyTimer {

	private final Cycle cycle = new Cycle();
	private final Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!cycle.increment()) {
				timer.stop();
				getRunOnStop().run();
			}
		}
	});
	
	private Runnable runOnStop = ()-> System.out.println("Stopped");
	
	public MyTimer() {
		timer.setRepeats(true);
	}
	
	public Runnable getRunOnStop() {
		return runOnStop;
	}

	public void setRunOnStop(Runnable runOnStop) {
		this.runOnStop = runOnStop;
	}

	public void addListener(ActionListener listener) {
		cycle.addListener(listener);
	}

	public void add(String action, int duration) {
		cycle.add(action, duration);
	}
	
	public void add(String action, int duration, Unit unit) {
		cycle.add(action, duration, unit);
	}
	
	public void start() {
		timer.start();
		cycle.fireEvent("Relax");
	}

	enum Unit {
		SECOND(1000), MINUTE(60 * 1000), HOUR(60 * 60 * 1000), DAY(60 * 60 * 24 * 1000);

		final int duration;

		Unit(int duration) {
			this.duration = duration;
		}
	}

	static class Cycle {
		private ActionListener listener;

		private final Map<Long, String> points = new LinkedHashMap<>();
		private long currentPoint;
		private long length;
		private long lastPoint;

		public boolean increment() {
			return increment(Unit.SECOND);
		}

		public boolean increment(Unit unit) {
			currentPoint += unit.duration;
			if (currentPoint == length) {
				boolean res = fireForPoint();
				currentPoint = 0;
				lastPoint = 0;
				return res;
			}
			return fireForPoint();
		}

		private boolean fireForPoint() {
			String action = points.get(currentPoint);
			if (action != null) {
				//System.out.println("duration= " + (currentPoint - lastPoint)/Unit.SECOND.duration);
				lastPoint = currentPoint;
				return fireEvent(action);
			}
			return true;
		}

		public void add(String action, int duration) {
			add(action, duration, Unit.SECOND);
		}

		public void add(String action, int duration, Unit unit) {
			length += duration * unit.duration;
			points.put(length, action);
		}

		public void addListener(ActionListener listener) {
			this.listener = AWTEventMulticaster.add(this.listener, listener);
		}

		public boolean fireEvent(String type) {
			if (listener != null) {
				try {
					listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, type));
					return true;
				} catch(Throwable t) {
					return false;
				}
			}
			else {
				return false;
			}
		}
	}

	public static class Action {
		private final String name;
		private Runnable run;
		private Action prevAction;
		
		protected Action(String name) {
			this.name = name;
		}

		public Action(String name, Runnable run) {
			this.name = name;
			this.run = run;
		}
		
		protected void setRunnable(Runnable r) {
			this.run = r;
		}

		public String getName() {
			return name;
		}
		
		public Runnable getRunnable() {
			return run;
		}

		public Action getPrevAction() {
			return prevAction;
		}
	}

	public static class ActionList {
		private final List<Action> actions = new ArrayList<>();
		private Action lastAction;
		
		public void add(Action action) {
			action.prevAction = lastAction;
			lastAction = action;
			actions.add(action);
		}
		
		public ActionIterator start() {
			return new ActionIterator();
		}

		public class ActionIterator {
			private final Iterator<Action> iterator;
			private final EmptyAction emptyAction = new EmptyAction();

			public ActionIterator() {
				this.iterator = new SafeIterator<>(actions.iterator());
			}
			
			public Action next() {
				final Action next = iterator.next();
				if(next != null) {
					return next;					
				}
				return emptyAction;
			}
			
			public boolean hasNext() {
				return iterator.hasNext();
			}
		}
	}
	
	static class PrintAction extends Action {
		private LocalTime time;
		
		public PrintAction(String action) {
			super(action);
			setRunnable(()-> {
				time = LocalTime.now();
				Action prevAction = getPrevAction();
				if(prevAction instanceof PrintAction) {
					LocalTime t0 = ((PrintAction) prevAction).time;
					System.out.println(action + ", duration=" + Duration.between(t0, time).getSeconds());
				}
			});
		}
		
		void setTime(LocalTime t) {
			this.time = t;
		}
	}
	
	static class EmptyAction extends Action {
		private static Runnable create() {
			return new Runnable() {
				@Override
				public void run() {
					throw new RuntimeException();
				}
			};
		}
		public EmptyAction() {
			super("Empty", create());
		}
	}

	public static void main(String[] args) {
		MyTimer myTimer = new MyTimer();
		myTimer.add("Work", 1, Unit.SECOND);
		myTimer.add("Relax", 5, Unit.SECOND);
		
		ActionList actionList = new ActionList();
		actionList.add(new PrintAction("Bergstellung"));
		actionList.add(new PrintAction("Sonnengruß"));
		actionList.add(new PrintAction("Adler"));
		actionList.add(new PrintAction("Baum"));
		actionList.add(new PrintAction("Bogen"));
		actionList.add(new PrintAction("Brücke"));
		actionList.add(new PrintAction("Dreieck"));
		actionList.add(new PrintAction("Hase"));
		actionList.add(new PrintAction("Held"));
		actionList.add(new PrintAction("Heuschrecke"));
		actionList.add(new PrintAction("Hund"));
		actionList.add(new PrintAction("Kamel"));
		actionList.add(new PrintAction("Katze"));
		actionList.add(new PrintAction("Kuhgesicht"));
		actionList.add(new PrintAction("Schmetterling"));
		actionList.add(new PrintAction("Seitstütz"));
		actionList.add(new PrintAction("Sphinx"));
		actionList.add(new PrintAction("Standwaage"));
		actionList.add(new PrintAction("Stütz"));
		actionList.add(new PrintAction("Tänzer"));
		actionList.add(new PrintAction("Taube"));
		actionList.add(new PrintAction("Vogel"));
		actionList.add(new PrintAction("Vorwärtsbeuge"));
		
		ActionIterator iterator = actionList.start();

		myTimer.addListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand().toLowerCase()) {
				case "work":
					iterator.next().getRunnable().run();
					break;
				case "relax":
					System.out.println("Relax");
				}
			}
		});
		myTimer.start();
		myTimer.setRunOnStop(() -> System.exit(0));
		new JFrame().setVisible(true);
	}
}

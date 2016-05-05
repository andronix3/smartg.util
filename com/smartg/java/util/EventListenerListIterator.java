package com.smartg.java.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Replace horrible loop over event listeners from EventListenerList with an Iterator.
 * @author andronix
 *
 * @param <T>
 */
public class EventListenerListIterator<T> implements Iterator<T> {

    private T next;
    private final Class<T> t;
    private int index;

    private final Object[] listeners;

    public EventListenerListIterator(Class<T> t, EventListenerList list) {
	this.listeners = list.getListenerList();
	index = listeners.length - 1;
	this.t = t;
	goNext();
    }

    public boolean hasNext() {
	return next != null;
    }

    public T next() {
	T tmp = next;
	next = null;
	goNext();
	return tmp;
    }

    @SuppressWarnings("unchecked")
    private void goNext() {
	Object nxt = null;
	while ((nxt == null || !t.isInstance(nxt)) && index >= 0) {
	    nxt = listeners[index];
	    index -= 2;
	}
	if (nxt != null && t.isInstance(nxt)) {
	    next = (T) nxt;
	}
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }

    public static void main(String... s) {
	EventListenerList list = new EventListenerList();
	list.add(ChangeListener.class, new ChangeListener0());
	list.add(MouseListener.class, new MouseAdapter() {});
	list.add(ActionListener.class, new ActionListener0());
	list.add(ChangeListener.class, new ChangeListener0());
	list.add(MouseListener.class, new MouseAdapter() {});
	list.add(ActionListener.class, new ActionListener0());
	list.add(ChangeListener.class, new ChangeListener0());
	list.add(MouseListener.class, new MouseAdapter() {});
	list.add(ActionListener.class, new ActionListener0());
	list.add(ChangeListener.class, new ChangeListener0());
	list.add(MouseListener.class, new MouseAdapter() {});
	list.add(ActionListener.class, new ActionListener0());

	EventListenerListIterator<ActionListener> iter = new EventListenerListIterator<ActionListener>(ActionListener.class, list);
	while(iter.hasNext()) {
	    System.out.println(iter.next());
	}
	
    }

    private static final class ActionListener0 implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	
	}
    }

    private static final class ChangeListener0 implements ChangeListener {
	public void stateChanged(ChangeEvent e) {

	}
    }

}
package com.smartg.java.util;

import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * AddToList ensures that you don't add the same event listener to EventListenerList twice
 * @author andro
 *
 */
public class AddToList {
    private EventListenerList list;

    public AddToList(EventListenerList list) {
	this.list = list;
    }

    public synchronized <T extends EventListener> void add(Class<T> t, T listener) {
	EventListenerListIterator<ActionListener> iter = new EventListenerListIterator<ActionListener>(ActionListener.class, list);
	while(iter.hasNext()) {
	    ActionListener next = iter.next();
	    if(next.equals(listener)) {
		return;
	    }
	}
	list.add(t, listener);
    }

}

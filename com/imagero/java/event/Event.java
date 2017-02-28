package com.imagero.java.event;

/**
 * Base event class.
 * @see BEvent
 * @see VEvent
 * 
 * @author andrey
 */
public class Event {
    public final int eventId;
    protected int subId;
    public final EventSource source;

    Object command;

    protected Event(EventSource source) {
        this.eventId = getID();
        this.subId = -1;
        this.source = source;
    }

    protected Event(EventSource source, int sid) {
        this.eventId = getID();
        this.subId = sid;
        this.source = source;
    }

    public Event(EventSource source, Object command) {
        this.eventId = getID();
        this.source = source;
        this.subId = command.hashCode();
        this.command = command;
    }

    protected final int getID() {
        return getClass().getName().hashCode();
    }

    public Object getCommand() {
        return command;
    }
}

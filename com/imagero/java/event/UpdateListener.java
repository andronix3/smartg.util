package com.imagero.java.event;


/**
 * Date: 30.03.2009
 * 
 * @author Andrey Kuznetsov
 */
public abstract class UpdateListener extends VEventListener {

    public UpdateListener() {
        super(UpdateEvent.class);
    }

    public UpdateListener(Object command) {
        super(UpdateEvent.class, command);
    }

    protected abstract void update(UpdateEvent e);

    protected void call(VEvent e) {
        update((UpdateEvent) e);
    }
}

package com.imagero.java.beans;

import java.util.HashMap;
import java.util.Map;

import com.smartg.java.util.StackTraceUtil;

public class HandlerList {

	private final Map<String, SimplePropertyHandler> map = new HashMap<>();

    /**
     * (de)register Handler for a property
     * @param key handler key, if key is null, this handler will be used for all unknown properties. If handler is null, it is same as removing a handler for a property.
     * @param h  handler
     */
    public void setHandler(String key, SimplePropertyHandler h) {
        map.put(key, h);
    }

    public void propertyChange(String key, Object property) {
        SimplePropertyHandler h = map.get(key);
        if (h != null) {
            h.propertyChange(property);
        } else {
            h = map.get(null);
            if (h != null) {
                h.propertyChange(property);
            } else {
                StackTraceUtil.warning("Handler not found for key: " + key);
            }
        }
    }
}

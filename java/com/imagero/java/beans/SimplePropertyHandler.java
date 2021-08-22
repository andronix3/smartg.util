package com.imagero.java.beans;

public interface SimplePropertyHandler {

	/**
     * The main idea is that Handler don't need to know property name, 
     * because we use a it only for single property.
     * Appropriate handler is choosed by HandlerList class.
     * 
     * @param newValue new value
     */
    void propertyChange(Object newValue);
    
}
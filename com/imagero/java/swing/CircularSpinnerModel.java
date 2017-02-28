package com.imagero.java.swing;

import javax.swing.AbstractSpinnerModel;

public class CircularSpinnerModel extends AbstractSpinnerModel {

    private static final long serialVersionUID = -3162885071448002119L;

    Object[] elements;
    int index;

    public CircularSpinnerModel(Object[] elements) {
	this.elements = elements;
    }

    public Object getNextValue() {
	if (++index >= elements.length) {
	    index = 0;
	}
	return elements[index];
    }

    public Object getPreviousValue() {
	if (--index < 0) {
	    index = elements.length - 1;
	}
	return elements[index];
    }

    public Object getValue() {
	return elements[index];
    }

    public void setValue(Object value) {
	elements[index] = value;
	fireStateChanged();
    }
}

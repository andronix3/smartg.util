package com.smartg.java.util;

import java.awt.Point;
import java.util.Iterator;

/**
 * Replace two for loops with iterator.
 * 
 * @author andrey
 *
 */
public class XY_Iterator implements Iterator<Point> {

    private int left;
    private int right, bottom;

    private int x, y;

    private Point next;

    public XY_Iterator(int width, int height) {
	this(0, 0, width, height);
    }

    public XY_Iterator(int left, int top, int right, int bottom) {
	this.left = left;
	this.right = right;
	this.bottom = bottom;
	
	x = left;
	y = top;

	goNext();
    }

    public boolean hasNext() {
	return next != null;
    }

    public Point next() {
	Point nxt = next;
	next = null;
	goNext();
	return nxt;
    }

    private void goNext() {
	if (x >= right - 1) {
	    y++;
	    x = left;
	    if (y < bottom) {
		next = new Point(x, y);
	    }
	} else {
	    next = new Point(x++, y);
	}
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }
}
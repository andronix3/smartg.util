package com.imagero.java.geom;

public class FInsets {
    public final float top;
    public final float left;
    public final float bottom;
    public final float right;
    public final float width;
    public final float height;
    
    private Insets ins;

    public FInsets(float top, float left, float bottom, float right) {
	this.top = top;
	this.left = left;
	this.bottom = bottom;
	this.right = right;
	width = right + left;
	height = top + bottom;
    }

    public boolean equals(Object obj) {
	if (obj != null && obj instanceof Insets) {
	    Insets insets = (Insets) obj;
	    return (top == insets.top) && (left == insets.left) && (right == insets.right) && (bottom == insets.bottom);
	}
	return false;
    }

    public int hashCode() {
	float hash = 5;
	hash = 83 * hash + top;
	hash = 83 * hash + left;
	hash = 83 * hash + bottom;
	hash = 83 * hash + right;
	return (int) hash;
    }

    public FInsets add(FInsets insets) {
	return new FInsets(top + insets.top, left + insets.left, bottom + insets.bottom, right + insets.right);
    }
    
    public Insets getIntegerInstance() {
	if(ins == null) {
	    ins = new Insets(Math.round(top), Math.round(left), Math.round(bottom), Math.round(right));
	}
	return ins;
    }
}

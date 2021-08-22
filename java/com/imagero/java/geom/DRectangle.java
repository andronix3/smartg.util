package com.imagero.java.geom;

public class DRectangle {
    public final double x;
    public final double x2;
    public final double y;
    public final double y2;
    public final double width;
    public final double height;

    public final DDimension size;

    public final DPoint topLeft;
    public final DPoint topRight;
    public final DPoint bottomLeft;
    public final DPoint bottomRight;

    IRectangle irect;
    FRectangle frect;

    @SuppressWarnings("CopyConstructorMissesField")
	public DRectangle(DRectangle r) {
		this(r.x, r.y, r.width, r.height);
    }

    public DRectangle(DPoint p1, DPoint p2) {
	this.x = Math.min(p1.x, p2.x);
	this.y = Math.min(p1.y, p2.y);
	this.x2 = Math.max(p1.x, p2.x);
	this.y2 = Math.max(p1.y, p2.y);

	this.width = this.x2 - this.x + 1;
	this.height = this.y2 - this.y + 1;

	this.size = new DDimension(width, height);

	this.topLeft = new DPoint(x, y);
	this.topRight = new DPoint(x2, y);
	this.bottomLeft = new DPoint(x, y2);
	this.bottomRight = new DPoint(x2, y2);
    }

    public DRectangle(double x, double y, double width, double height) {
	this.x = Math.min(x, x + width);
	this.x2 = Math.max(x, x + width) - 1;
	this.y = Math.min(y, y + height);
	this.y2 = Math.max(y, y + height) - 1;

	this.width = width;
	this.height = height;

	this.size = new DDimension(width, height);

	this.topLeft = new DPoint(x, y);
	this.topRight = new DPoint(x2, y);
	this.bottomLeft = new DPoint(x, y2);
	this.bottomRight = new DPoint(x2, y2);
    }

    public DRectangle move(double x, double y) {
	return new DRectangle(this.x + x, this.y + y, width, height);
    }

    public DRectangle crop(DRectangle r) {
	return intersection(r);
    }
    
    public DRectangle reduce(FInsets insets) {
	return new DRectangle(new DPoint(x + insets.left, y + insets.top), new DPoint(x2 - insets.right, y2 - insets.bottom));
    }
    
    public DRectangle expand(FInsets insets) {
	return new DRectangle(new DPoint(x - insets.left, y - insets.top), new DPoint(x2 + insets.right, y2 + insets.bottom));
    }

    public DRectangle moveTo(double x, double y) {
	return new DRectangle(x, y, width, height);
    }

    public DRectangle rotate() {
		//noinspection SuspiciousNameCombination
		return new DRectangle(x, y, height, width);
    }

    public DRectangle scale(double scale) {
	return new DRectangle(x * scale, y * scale, x2 * scale, y2 * scale);
    }

    public DRectangle scale(double scaleX, double scaleY) {
	return new DRectangle(x * scaleX, y * scaleY, x2 * scaleX, y2 * scaleY);
    }

    public boolean contains(double x, double y) {
	return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
    }

    public boolean intersects(DRectangle r) {
		return (!(x >= r.x2)) && (!(x2 <= r.x)) && (!(y >= r.y2)) && (!(y2 <= r.y));
	}

    public DRectangle intersection(DRectangle r) {
	double _x1 = Math.max(x, r.x);
	double _x2 = Math.min(x2, r.x2);
	double _y1 = Math.max(y, r.y);
	double _y2 = Math.min(y2, r.y2);
	if (intersects(r)) {
	    return new DRectangle(new DPoint(_x1, _y1), new DPoint(_x2, _y2));
	}
	return new DRectangle(_x1, _y1, Math.max(0, _x2 - _x1 + 1), Math.max(0, _y2 - _y1 + 1));
    }

    public DRectangle translate(int tx, int ty) {
	return new DRectangle(tx + x, ty + y, width, height);
    }

    public DRectangle union(DRectangle r) {
	double _x1 = Math.min(x, r.x);
	double _x2 = Math.max(x2, r.x2);
	double _y1 = Math.min(y, r.y);
	double _y2 = Math.max(y2, r.y2);
	return new DRectangle(_x1, _y1, _x2 - _x1 + 1, _y2 - _y1 + 1);
    }

    public boolean contains2(int x, int y) {
	return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
    }

    public int hashCode() {
	double hash = 5;
	hash = 83 * hash + this.x;
	hash = 83 * hash + this.y;
	hash = 83 * hash + this.width;
	hash = 83 * hash + this.height;
	return (int) hash;
    }

    public boolean equals(Object obj) {
	if (obj instanceof DRectangle) {
	    DRectangle r = (DRectangle) obj;
	    return (x == r.x) && (y == r.y) && (x2 == r.x2) && (y2 == r.y2);
	}
	return false;
    }

    public String toString() {
	return "FRectangle: " + x + ", " + y + ", " + width + ", " + height;
    }

    public IRectangle getIntegerInstance() {
	if (irect == null) {
	    IPoint p1 = new IPoint((int)Math.round(x), (int)Math.round(y));
	    IPoint p2 = new IPoint((int)Math.round(x2), (int)Math.round(y2));
	    irect = new IRectangle(p1, p2);
	    irect.drect = this;
	}
	return irect;
    }
    
    public FRectangle getFloatInstance() {
	if (frect == null) {
	    FPoint p1 = new FPoint((float)x, (float)y);
	    FPoint p2 = new FPoint((float)x2, (float)y2);
	    frect = new FRectangle(p1, p2);
	    frect.drect = this;
	}
	return frect;
    }
}

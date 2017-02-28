package com.imagero.java.geom;

public class DPoint {

    public final double x;
    public final double y;
    
    IPoint ipoint;
    FPoint fpoint;

    public DPoint() {
        x = 0;
        y = 0;
    }

    public DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof FPoint) {
            FPoint p = (FPoint) obj;
            return (x == p.x) && (y == p.y);
        }
        return false;
    }

    public int hashCode() {
	double hash = 5;
        hash = 83 * hash + this.x;
        hash = 83 * hash + this.y;
        return (int) hash;
    }

    public String toString() {
        return "DPoint: (" + x + ", " + y + ")";
    }
    
    public IPoint getIntegerInstance() {
	if(ipoint == null) {
	    ipoint = new IPoint((int)Math.round(x), (int)Math.round(y));
	    ipoint.dpoint = this;
	}
	return ipoint;
    }
    
    public FPoint getFloatInstance() {
	if(fpoint == null) {
	    fpoint = new FPoint((float)x, (float)y);
	    fpoint.dpoint = this;
	}
	return fpoint;
    }
}

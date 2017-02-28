package com.imagero.java.geom;


public class DDimension {
    public final double width;
    public final double height;
    
    IDimension idim;
    FDimension fdim;

    public DDimension(DDimension d) {
        this.width = d.width;
        this.height = d.height;
    }

    public DDimension(double w, double h) {
        this.width = w;
        this.height = h;
    }
    
    public IDimension getIntegerInstance() {
	if(idim == null) {
	    idim = new IDimension((int)Math.round(width), (int)Math.round(height));
	    idim.ddim = this;
	}
	return idim;
    }
    
    public FDimension getFloatInstance() {
	if(fdim == null) {
	    fdim = new FDimension((float)width, (float)height);
	    fdim.ddim = this;
	}
	return fdim;
    }
}

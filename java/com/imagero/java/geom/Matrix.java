package com.imagero.java.geom;

import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.logging.Logger;

public abstract class Matrix {
    public final int width;
    public final int height;

    public final int sizeX, sizeY;

    private int[][] indexes;

    public Matrix(int size) {
	this.sizeX = size;
	this.sizeY = size;

	width = size + size + 1;
	height = size + size + 1;

	init();
    }

    public Matrix(int sizeX, int sizeY) {
	this.sizeX = sizeX;
	this.sizeY = sizeY;

	width = sizeX + sizeX + 1;
	height = sizeY + sizeY + 1;

	init();
    }
    
    public Matrix(IDimension d) {
	this.width = d.width;
	this.height = d.height;
	if((d.width & 1) == 0) {
	    sizeX = d.width / 2;
	}
	else {
	    sizeX = (d.width - 1) / 2;
	}
	if((d.height & 1) == 0) {
	    sizeY = d.height / 2;
	}
	else {
	    sizeY = (d.height - 1) / 2;
	}
	init();
    }

    public void set(Number[] data) {
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		int index = getIndex(x, y);
		set(x, y, data[index]);
	    }
	}
    }

    public void set(int[] data) {
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		int index = getIndex(x, y);
		set(x, y, data[index]);
	    }
	}
    }

    public void set(float[] data) {
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		int index = getIndex(x, y);
		set(x, y, data[index]);
	    }
	}
    }

    private void init() {
	indexes = new int[height][width];
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		indexes[y][x] = computeIndex(x, y);
	    }
	}
    }
    
    public double getMaxValue() {
	double max = 0;
	for(int y = 0; y < height; y++) {
	    for(int x = 0; x < width; x++) {
		double f = get(x, y).doubleValue();
		if(f > max) {
		    max = f;
		}
	    }
	}
	return max;
    }

    public abstract void set(int x, int y, Number value);

    abstract public Number get(int x, int y);

    private int computeIndex(int x, int y) {
	return width * y + x;
    }

    protected final int getIndex(int x, int y) {
	return indexes[y][x];
    }

    public void rotateLeft() {
	Number[] colData = getColumn(0);

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width - 1; x++) {
		set(x, y, get(x + 1, y));
	    }
	}
	setColumn(width - 1, colData);
    }

    public void rotateRight() {
	Number[] colData = getColumn(width - 1);

	for (int y = 0; y < height; y++) {
	    for (int x = width - 1; x > 0; x--) {
		set(x, y, get(x - 1, y));
	    }
	}
	setColumn(0, colData);
    }

    public void rotateUp() {
	Number[] firstRow = getRow(0);

	for (int y = 0; y < height - 1; y++) {
	    setRow(y, getRow(y + 1));
	}

	setRow(height - 1, firstRow);
    }

    public void rotateDown() {
	Number[] lastRow = getRow(height - 1);

	for (int y = height - 1; y > 0; y--) {
	    setRow(y, getRow(y - 1));
	}

	setRow(0, lastRow);
    }

    public Number[] getRow(int y) {
	Number[] res = new Number[width];
	return getRow(y, res);
    }

    public Number[] getRow(int y, Number[] rowData) {
	for (int x = 0; x < width; x++) {
	    rowData[x] = get(x, y);
	}
	return rowData;
    }

    public void setRow(int y, Number[] rowData) {
	for (int x = 0; x < width; x++) {
	    set(x, y, rowData[x]);
	}
    }

    public Number[] getColumn(int x) {
	Number[] res = new Number[height];
	return getColumn(x, res);
    }

    public void setColumn(int x, Number[] colData) {
	for (int y = 0; y < height; y++) {
	    set(x, y, colData[y]);
	}
    }

    public Number[] getColumn(int x, Number[] colData) {
	for (int y = 0; y < height; y++) {
	    colData[y] = get(x, y);
	}
	return colData;
    }

    public int[][] getIndexes() {
	return indexes.clone();
    }

    public Enumeration<Number> values() {
	return new MatrixEnumerator(this);
    }

    public void print() {
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	Logger l = Logger.getLogger("com.imagero.java.geom");

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		float f = get(x, y).floatValue();
		l.info(nf.format(f) + "\t");
	    }
	    l.info("\n");
	}
	l.info("\n");
    }

    public Number checksum() {
		double sum = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sum += get(x, y).doubleValue();
			}
		}
		return sum;
	}

    public double sum() {
		double res = 0;
		Enumeration<Number> values = values();
		while (values.hasMoreElements()) {
			Number n = values.nextElement();
			res += n.doubleValue();
		}
		return res;
	}
    
    static class MatrixEnumerator implements Enumeration<Number> {
	Matrix m;
	int x, y;
	final int w, h;
	int p = 0;

	Number[] values;

	MatrixEnumerator(Matrix m) {
		this.m = m;
		w = m.width;
		h = m.height;
		values = new Number[w * h];
		for (int y = 0, p = 0; y < h; y++) {
			for (x = 0; x < w; x++) {
				values[p++] = m.get(x, y);
			}
		}
	}

	public boolean hasMoreElements() {
	    return p < values.length;
	}

	public Number nextElement() {
	    return values[p++];
	}
    }

    // public static void main(String[] args) {
    // int w = 2;
    // int h = 2;
    // Matrix m = new IntMatrix(w, h);
    //
    // for (int y = 0; y < m.height; y++) {
    // for (int x = 0; x < m.width; x++) {
    // m.set(x, y, new Integer(x));
    // }
    // }
    //
    // m.print();
    //	
    // m.rotateRight();
    // m.print();
    //	
    // m.rotateRight();
    // m.print();
    //
    // m.rotateRight();
    // m.print();
    //	
    // m.rotateRight();
    // m.print();
    //	
    // m.rotateRight();
    // m.print();
    //
    //	
    // // m.rotateUp();
    // // m.print();
    //
    //
    // }

}

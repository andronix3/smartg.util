package com.imagero.java.geom;

public class IntMatrix extends Matrix {

    int[] matrix;

    public IntMatrix(int size) {
	super(size);
	matrix = new int[width * height];
    }
    
    public IntMatrix(int sizeX, int sizeY) {
	super(sizeX, sizeY);
	matrix = new int[width * height];
    }

    public IntMatrix(IDimension d) {
	super(d);
	matrix = new int[width * height];
    }

    public void set(int x, int y, Number value) {
	int index = getIndex(x, y);
	matrix[index] = value.intValue();
    }

    public Number get(int x, int y) {
	int index = getIndex(x, y);
	return new Float(matrix[index]);
    }

    public int getInt(int x, int y) {
	int index = getIndex(x, y);
	return matrix[index];
    }

    public int[] getMatrix() {
	return matrix.clone();
    }

    public int[][] get2dMatrix() {
	int[][] result = new int[height][width];
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		result[y][x] = matrix[getIndex(x, y)];
	    }
	}
	return result;
    }
}

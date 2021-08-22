package com.imagero.java.geom;

public class FloatMatrix extends Matrix {

    float[] matrix;

    public FloatMatrix(int size) {
	super(size);
	matrix = new float[width * height];
    }

    public FloatMatrix(int sizeX, int sizeY) {
	super(sizeX, sizeY);
	matrix = new float[width * height];
    }

    public FloatMatrix(IDimension d) {
	super(d);
	matrix = new float[width * height];
    }

    public void set(int x, int y, Number value) {
	int index = getIndex(x, y);
	matrix[index] = value.floatValue();
    }

    public Number get(int x, int y) {
	int index = getIndex(x, y);
	return matrix[index];
    }

    public float getFloat(int x, int y) {
	int index = getIndex(x, y);
	return matrix[index];
    }

    public float[] getMatrix() {
	return matrix.clone();
    }

    public float[][] get2dMatrix() {
	float[][] result = new float[height][width];
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		result[y][x] = matrix[getIndex(x, y)];
	    }
	}
	return result;
    }

    public float[] getRowFloat(int y) {
	float[] result = new float[width];
	for (int x = 0; x < width; x++) {
	    result[x] = matrix[getIndex(x, y)];
	}
	return result;
    }
}

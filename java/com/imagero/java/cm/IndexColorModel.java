/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagero.java.cm;


/**
 *
 * @author andrey
 */
public class IndexColorModel extends ColorModel {

    private int pixelSize;
    private int[] idata;
    private byte[] r;
    private byte[] g;
    private byte[] b;
    private byte[] a;

    public IndexColorModel(int pixelSize, byte[] r, byte[] g, byte[] b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.pixelSize = pixelSize;
        idata = new int[r.length];
        for (int i = 0; i < idata.length; i++) {
            idata[i] = (0xFF << 24) | ((r[i] & 0xFF) << 16) | ((g[i] & 0xFF) << 8) | (b[i] & 0xFF);
        }
    }

    public IndexColorModel(int pixelSize, byte[] r, byte[] g, byte[] b, byte[] a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.pixelSize = pixelSize;
        idata = new int[r.length];
        for (int i = 0; i < idata.length; i++) {
            idata[i] = ((a[i] & 0xFF) << 24) | ((r[i] & 0xFF) << 16) | ((g[i] & 0xFF) << 8) | (b[i] & 0xFF);
        }
    }

    /**
     * Create IndexColorModel
     * @param pixelSize bits per pixel
     * @param data color data
     * @param bypp bytes per pixel
     * @param offsets color offsets valid values are: {r, g, b, a}, {r, g, b}, {grey, a} or {grey}
     */
    public IndexColorModel(int pixelSize, byte[] data, int bypp, int[] offsets) {
        int length = data.length / bypp;

        this.r = new byte[length];
        this.g = new byte[length];
        this.b = new byte[length];
        this.a = new byte[length];
        this.pixelSize = pixelSize;
        idata = new int[r.length];

        fillData(offsets, length, data, bypp);

        for (int i = 0; i < idata.length; i++) {
            idata[i] = ((a[i] & 0xFF) << 24) | ((r[i] & 0xFF) << 16) | ((g[i] & 0xFF) << 8) | (b[i] & 0xFF);
        }
    }

    public int getRed(int pixel) {
        return r[pixel];
    }

    public int getGreen(int pixel) {
        return g[pixel];
    }

    public int getBlue(int pixel) {
        return b[pixel];
    }

    public int getAlpha(int pixel) {
        return a != null ? a[pixel] : 0xFF;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public int getRGB(int pixel) {
        return idata[pixel];
    }

    private void fillData(int[] offsets, int length, byte[] data, int bypp) throws IllegalArgumentException {
        switch (offsets.length) {
            case 4:
                for (int i = 0, p = 0; i < length; i++) {
                    r[i] = data[p + offsets[0]];
                    g[i] = data[p + offsets[1]];
                    b[i] = data[p + offsets[2]];
                    a[i] = data[p + offsets[3]];
                    p += bypp;
                }
                break;
            case 3:
                for (int i = 0, p = 0; i < length; i++) {
                    r[i] = data[p + offsets[0]];
                    g[i] = data[p + offsets[1]];
                    b[i] = data[p + offsets[2]];
                    a[i] = (byte) 0xFF;
                    p += bypp;
                }
                break;
            case 2:
                for (int i = 0, p = 0; i < length; i++) {
                    byte x = data[p + offsets[0]];
                    r[i] = x;
                    g[i] = x;
                    b[i] = x;
                    a[i] = data[p + offsets[1]];
                    p += bypp;
                }
                break;
            case 1:
                for (int i = 0, p = 0; i < length; i++) {
                    byte x = data[p + offsets[0]];
                    r[i] = x;
                    g[i] = x;
                    b[i] = x;
                    a[i] = (byte) 0xFF;
                    p += bypp;
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}

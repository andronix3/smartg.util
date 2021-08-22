/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagero.java.cm;


/**
 *
 * @author andrey
 */
public class DirectColorModel extends ColorModel {

    static final DirectColorModel instance = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF, 0xFF000000);
    
    /**
     * masks
     */
    int rm, gm, bm, am;
    /**
     * shifts right
     */
    int rs, gs, bs, as;
    /**
     * shifts left
     */
    int rx, gx, bx, ax;
    
    int bitsPerPixel;
    
    boolean hasAlpha;

    public DirectColorModel(int bps, int rmask, int gmask, int bmask, int amask) {
        bitsPerPixel = bps;
        hasAlpha = amask > 0;

        rm = rmask;
        gm = gmask;
        bm = bmask;

        rs = computeShift(rm);
        gs = computeShift(gm);
        bs = computeShift(bm);

        rx = computeScale((rm >> rx) & 0xFF);
        gx = computeScale((rm >> gx) & 0xFF);
        bx = computeScale((rm >> bx) & 0xFF);

        if (hasAlpha) {
            am = amask;
            as = computeShift(am);
            ax = computeScale((rm >> ax) & 0xFF);
        }
    }

    public int getAlpha(int pixel) {
        if (hasAlpha) {
            return ((pixel & am) >> as) << ax;
        }
        else {
            return 0xFF;
        }
    }

    public int getRed(int pixel) {
        return ((pixel & rm) >> rs) << rx;
    }

    public int getGreen(int pixel) {
        return ((pixel & gm) >> gs) << gx;
    }

    public int getBlue(int pixel) {
        return ((pixel & bm) >> bs) << bx;
    }

    public int getPixelSize() {
        return bitsPerPixel;
    }

    public int getRGB(int pixel) {
        return pixel;
    }

    protected int computeShift(int m) {
        int k = 0;
        while ((m & 1) == 0) {
            m = m >>> 1;
            k++;
        }
        return k;
    }

    protected int computeScale(int m) {
        int k = 0;
        while ((m & 1) == 1) {
            m = m >>> 1;
            k++;
        }
        return 8 - k;
    }
}

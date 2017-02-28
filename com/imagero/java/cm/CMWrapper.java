package com.imagero.java.cm;

import java.awt.image.ColorModel;

/**
 * Date: 25.05.2008
 *
 * @author Andrey Kuznetsov
 */
public class CMWrapper extends com.imagero.java.cm.ColorModel {
    ColorModel cm;

    public CMWrapper(ColorModel cm) {
        this.cm = cm;
    }

    public int getRed(int pixel) {
        return cm.getRed(pixel);
    }

    public int getGreen(int pixel) {
        return cm.getGreen(pixel);
    }

    public int getBlue(int pixel) {
        return cm.getBlue(pixel);
    }

    public int getAlpha(int pixel) {
        return cm.getAlpha(pixel);
    }

    public int getPixelSize() {
        return cm.getPixelSize();
    }
}

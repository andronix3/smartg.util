/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagero.java.cm;

/**
 *
 * @author andrey
 */
public abstract class ColorModel {

    public static ColorModel getRGBdefault() {
        return DirectColorModel.instance;
    }

    public abstract int getRed(int pixel);

    public abstract int getGreen(int pixel);

    public abstract int getBlue(int pixel);

    public abstract int getAlpha(int pixel);

    public int getRGB(int pixel) {
        return (getAlpha(pixel) << 24) | (getRed(pixel) << 16) | (getGreen(pixel) << 8) | (getBlue(pixel));
    }

    public abstract int getPixelSize();
}

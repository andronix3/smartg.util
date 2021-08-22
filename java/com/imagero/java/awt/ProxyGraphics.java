/*
 * Copyright (c) imagero Andrey Kuznetsov. All Rights Reserved.
 * http://jgui.imagero.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.imagero.java.awt;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * ProxyGraphics - just forward all calss to substituted Graphics
 * @author Andrey Kuznetsov
 */
public abstract class ProxyGraphics extends Graphics2D {

    
    
    abstract protected Graphics2D get();

    public void translate(int x, int y) {
        get().translate(x, y);
    }

    public Color getColor() {
        return get().getColor();
    }

    public void setColor(Color c) {
        get().setColor(c);
    }

    public void setPaintMode() {
        get().setPaintMode();
    }

    public void setXORMode(Color c1) {
        get().setXORMode(c1);
    }

    public Font getFont() {
        return get().getFont();
    }

    public void setFont(Font font) {
        get().setFont(font);
    }

    public FontMetrics getFontMetrics(Font f) {
        return get().getFontMetrics(f);
    }

    public Rectangle getClipBounds() {
        return get().getClipBounds();
    }

    public void clipRect(int x, int y, int width, int height) {
        get().clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        get().setClip(x, y, width, height);
    }

    public Shape getClip() {
        return get().getClip();
    }

    public void setClip(Shape clip) {
        get().setClip(clip);
    }

    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        get().copyArea(x, y, width, height, dx, dy);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        get().drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        get().fillRect(x, y, width, height);
    }

    public void clearRect(int x, int y, int width, int height) {
        get().clearRect(x, y, width, height);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        get().drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        get().fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void drawOval(int x, int y, int width, int height) {
        get().drawOval(x, y, width, height);
    }

    public void fillOval(int x, int y, int width, int height) {
        get().fillOval(x, y, width, height);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        get().drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        get().fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        get().drawPolyline(xPoints, yPoints, nPoints);
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        get().drawPolygon(xPoints, yPoints, nPoints);
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        get().fillPolygon(xPoints, yPoints, nPoints);
    }

    public void drawString(String str, int x, int y) {
        get().drawString(str, x, y);
    }

    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        get().drawString(iterator, x, y);
    }

    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return get().drawImage(img, x, y, observer);
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return get().drawImage(img, x, y, width, height, observer);
    }

    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return get().drawImage(img, x, y, bgcolor, observer);
    }

    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return get().drawImage(img, x, y, width, height, bgcolor, observer);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return get().drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return get().drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    public void dispose() {
        get().dispose();
    }

    public void draw(Shape s) {
        get().draw(s);
    }

    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return get().drawImage(img, xform, obs);
    }

    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        get().drawImage(img, op, x, y);
    }

    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        get().drawRenderedImage(img, xform);
    }

    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        get().drawRenderableImage(img, xform);
    }

    public void drawString(String s, float x, float y) {
        get().drawString(s, x, y);
    }

    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        get().drawString(iterator, x, y);
    }

    public void drawGlyphVector(GlyphVector gv, float x, float y) {
        get().drawGlyphVector(gv, x, y);
    }

    public void fill(Shape s) {
        get().fill(s);
    }

    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return get().hit(rect, s, onStroke);
    }

    public GraphicsConfiguration getDeviceConfiguration() {
        return get().getDeviceConfiguration();
    }

    public void setComposite(Composite comp) {
        get().setComposite(comp);
    }

    public void setPaint(Paint paint) {
        get().setPaint(paint);
    }

    public void setStroke(Stroke s) {
        get().setStroke(s);
    }

    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        get().setRenderingHint(hintKey, hintValue);
    }

    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return get().getRenderingHint(hintKey);
    }

    public void setRenderingHints(Map<?, ?> hints) {
        get().setRenderingHints(hints);
    }

    public void addRenderingHints(Map<?, ?> hints) {
        get().addRenderingHints(hints);
    }

    public RenderingHints getRenderingHints() {
        return get().getRenderingHints();
    }

    public void translate(double tx, double ty) {
        get().translate(tx, ty);
    }

    public void rotate(double theta) {
        get().rotate(theta);
    }

    public void rotate(double theta, double x, double y) {
        get().rotate(theta, x, y);
    }

    public void scale(double sx, double sy) {
        get().scale(sx, sy);
    }

    public void shear(double shx, double shy) {
        get().shear(shx, shy);
    }

    public void transform(AffineTransform Tx) {
        get().transform(Tx);
    }

    public void setTransform(AffineTransform Tx) {
        get().setTransform(Tx);
    }

    public AffineTransform getTransform() {
        return get().getTransform();
    }

    public Paint getPaint() {
        return get().getPaint();
    }

    public Composite getComposite() {
        return get().getComposite();
    }

    public void setBackground(Color color) {
        get().setBackground(color);
    }

    public Color getBackground() {
        return get().getBackground();
    }

    public Stroke getStroke() {
        return get().getStroke();
    }

    public void clip(Shape s) {
        get().clip(s);
    }

    public FontRenderContext getFontRenderContext() {
        return get().getFontRenderContext();
    }    
}

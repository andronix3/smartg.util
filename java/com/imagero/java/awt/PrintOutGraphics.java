/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
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
import java.awt.Graphics;
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
import java.util.Objects;

/**
 * ProxyGraphics - just forward all calss to substituted Graphics
 * 
 * @author Andrey Kuznetsov
 */
public class PrintOutGraphics extends Graphics2D {

	private final Graphics2D graphics;

	public PrintOutGraphics(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		//ensure we don't wrap second time
		while(graphics instanceof PrintOutGraphics) {
			graphics = ((PrintOutGraphics)graphics).get();
		}
		this.graphics = graphics;
	}

	private Graphics2D get() {
		return graphics;
	}

	public void translate(int x, int y) {
		System.out.println("translate(" + x + ", " + y + ")");
		get().translate(x, y);
	}

	public Color getColor() {
		return get().getColor();
	}

	public void setColor(Color c) {
		System.out.println("setColor(" + c + ")");
		get().setColor(c);
	}

	public void setPaintMode() {
		System.out.println("setPaintMode()");
		get().setPaintMode();
	}

	public void setXORMode(Color c1) {
		System.out.println("setXORMode()");
		get().setXORMode(c1);
	}

	public Font getFont() {
		return get().getFont();
	}

	public void setFont(Font font) {
		System.out.println("setFont(" + font + ")");
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
		System.out.println("clipRect(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public void setClip(int x, int y, int width, int height) {
		get().setClip(x, y, width, height);
		System.out.println("setClip(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public Shape getClip() {
		return get().getClip();
	}

	public void setClip(Shape clip) {
		get().setClip(clip);
		System.out.println("setClip(" + clip + ")");
	}

	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		get().copyArea(x, y, width, height, dx, dy);
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		get().drawLine(x1, y1, x2, y2);
		System.out.println("drawLine(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")");
	}

	public void fillRect(int x, int y, int width, int height) {
		get().fillRect(x, y, width, height);
		System.out.println("fillRect(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public void clearRect(int x, int y, int width, int height) {
		get().clearRect(x, y, width, height);
		System.out.println("clearRect(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		get().drawRoundRect(x, y, width, height, arcWidth, arcHeight);
		System.out.println("drawRoundRect(" + x + ", " + y + ", " + width + ", " + height + ", " + arcWidth + ", "
				+ arcHeight + ")");
	}

	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		get().fillRoundRect(x, y, width, height, arcWidth, arcHeight);
		System.out.println("fillRoundRect(" + x + ", " + y + ", " + width + ", " + height + ", " + arcWidth + ", "
				+ arcHeight + ")");
	}

	public void drawOval(int x, int y, int width, int height) {
		get().drawOval(x, y, width, height);
		System.out.println("drawOval(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public void fillOval(int x, int y, int width, int height) {
		get().fillOval(x, y, width, height);
		System.out.println("fillOval(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		get().drawArc(x, y, width, height, startAngle, arcAngle);
		System.out.println(
				"drawArc(" + x + ", " + y + ", " + width + ", " + height + ", " + startAngle + ", " + arcAngle + ")");
	}

	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		get().fillArc(x, y, width, height, startAngle, arcAngle);
		System.out.println(
				"fillArc(" + x + ", " + y + ", " + width + ", " + height + ", " + startAngle + ", " + arcAngle + ")");
	}

	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		get().drawPolyline(xPoints, yPoints, nPoints);
		System.out.println("drawPolyline()");
	}

	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		get().drawPolygon(xPoints, yPoints, nPoints);
		System.out.println("drawPolygon()");
	}

	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		get().fillPolygon(xPoints, yPoints, nPoints);
		System.out.println("fillPolygon()");
	}

	public void drawString(String str, int x, int y) {
		get().drawString(str, x, y);
		System.out.println("drawString(" + str + ", " + x + ", " + y + ")");
	}

	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		get().drawString(iterator, x, y);
		System.out.println("drawString(" + iterator + ", " + x + ", " + y + ")");
	}

	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		System.out.println("drawImage(" + x + ", " + y + ")");
		return get().drawImage(img, x, y, observer);
	}

	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		System.out.println("drawImage(" + x + ", " + y + width + ", " + height + ")");
		return get().drawImage(img, x, y, width, height, observer);
	}

	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		System.out.println("drawImage(" + x + ", " + y + bgcolor + ")");
		return get().drawImage(img, x, y, bgcolor, observer);
	}

	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		System.out.println("drawImage(" + x + ", " + y + ", " + width + ", " + height + ", " + bgcolor + ")");
		return get().drawImage(img, x, y, width, height, bgcolor, observer);
	}

	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) {
		System.out.println("drawImage(" + dx1 + ", " + dy1 + ", " + dx2 + ", " + dy2 + ", " + sx1 + ", " + sy1 + ", "
				+ sx2 + ", " + sy2 + ", " + ")");
		return get().drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}

	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {
		System.out.println("drawImage(" + dx1 + ", " + dy1 + ", " + dx2 + ", " + dy2 + ", " + sx1 + ", " + sy1 + ", "
				+ sx2 + ", " + sy2 + ", " + bgcolor + ")");
		return get().drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
	}

	public void dispose() {
		System.out.println("dispose");
		get().dispose();
	}

	public void draw(Shape s) {
		System.out.println("draw(" + s + ")");
		get().draw(s);
	}

	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		System.out.println("drawImage(" + xform + ")");
		return get().drawImage(img, xform, obs);
	}

	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		System.out.println("drawImage(" + op + ", " + x + ", " + y + ")");
		get().drawImage(img, op, x, y);
	}

	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		System.out.println("drawRenderedImage(" + xform + ")");
		get().drawRenderedImage(img, xform);
	}

	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		System.out.println("drawRenderableImage(" + xform + ")");
		get().drawRenderableImage(img, xform);
	}

	public void drawString(String s, float x, float y) {
		System.out.println("drawString(" + s + ", " + x + ", " + y + ")");
		get().drawString(s, x, y);
	}

	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
		System.out.println("drawString(" + iterator + ", " + x + ", " + y + ")");
		get().drawString(iterator, x, y);
	}

	public void drawGlyphVector(GlyphVector gv, float x, float y) {
		System.out.println("drawGlyphVector(" + gv + ", " + x + ", " + y + ")");
		get().drawGlyphVector(gv, x, y);
	}

	public void fill(Shape s) {
		System.out.println("fill(" + s + ")");
		get().fill(s);
	}

	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		boolean hit = get().hit(rect, s, onStroke);
		System.out.println("hit(" + s + ", "+onStroke + ") = " + hit);
		return hit;
	}

	public GraphicsConfiguration getDeviceConfiguration() {
		return get().getDeviceConfiguration();
	}

	public void setComposite(Composite comp) {
		System.out.println("setComposite(" + comp + ")");
		get().setComposite(comp);
	}

	public void setPaint(Paint paint) {
		System.out.println("setPaint(" + paint + ")");
		get().setPaint(paint);
	}

	public void setStroke(Stroke s) {
		System.out.println("setStroke(" + s + ")");
		get().setStroke(s);
	}

	public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
		System.out.println("setRenderingHint(" + hintKey + ", " + hintValue + ")");
		get().setRenderingHint(hintKey, hintValue);
	}

	public Object getRenderingHint(RenderingHints.Key hintKey) {
		return get().getRenderingHint(hintKey);
	}

	public void setRenderingHints(Map<?, ?> hints) {
		System.out.println("setRenderingHints(" + hints + ")");
		get().setRenderingHints(hints);
	}

	public void addRenderingHints(Map<?, ?> hints) {
		System.out.println("addRenderingHints(" + hints + ")");
		get().addRenderingHints(hints);
	}

	public RenderingHints getRenderingHints() {
		return get().getRenderingHints();
	}

	public void translate(double tx, double ty) {
		System.out.println("translate(" + tx + ", " + ty + ")");
		get().translate(tx, ty);
	}

	public void rotate(double theta) {
		System.out.println("rotate(" + theta + ")");
		get().rotate(theta);
	}

	public void rotate(double theta, double x, double y) {
		System.out.println("rotate(" + theta + ", " + x + ", " + y + ")");
		get().rotate(theta, x, y);
	}

	public void scale(double sx, double sy) {
		System.out.println("scale(" + sx + ", " + sy + ")");
		get().scale(sx, sy);
	}

	public void shear(double shx, double shy) {
		System.out.println("shear(" + shx + ", " + shy + ")");
		get().shear(shx, shy);
	}

	public void transform(AffineTransform Tx) {
		System.out.println("transform(" + Tx + ")");
		get().transform(Tx);
	}

	public void setTransform(AffineTransform Tx) {
		System.out.println("setTransform(" + Tx + ")");
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
		System.out.println("setBackground(" + color + ")");
		get().setBackground(color);
	}

	public Color getBackground() {
		return get().getBackground();
	}

	public Stroke getStroke() {
		return get().getStroke();
	}

	public void clip(Shape s) {
		System.out.println("clip(" + s + ")");
		get().clip(s);
	}

	public FontRenderContext getFontRenderContext() {
		return get().getFontRenderContext();
	}

	@Override
	public Graphics create() {
		return new PrintOutGraphics((Graphics2D) get().create());
	}
}

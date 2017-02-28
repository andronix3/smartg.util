/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.imagero.java.geom;

/**
 * 
 * @author andrey
 */
public class FRectangle {

    public final float x;
    public final float x2;
    public final float y;
    public final float y2;
    public final float width;
    public final float height;

    public final FDimension size;

    public final FPoint topLeft;
    public final FPoint topRight;
    public final FPoint bottomLeft;
    public final FPoint bottomRight;

    IRectangle rect;
    DRectangle drect;

    public FRectangle(FRectangle r) {
	this(r.x, r.y, r.width, r.height);
    }

    public FRectangle(FPoint p1, FPoint p2) {
	this.x = Math.min(p1.x, p2.x);
	this.y = Math.min(p1.y, p2.y);
	this.x2 = Math.max(p1.x, p2.x);
	this.y2 = Math.max(p1.y, p2.y);

	this.width = this.x2 - this.x + 1;
	this.height = this.y2 - this.y + 1;

	this.size = new FDimension(width, height);

	this.topLeft = new FPoint(x, y);
	this.topRight = new FPoint(x2, y);
	this.bottomLeft = new FPoint(x, y2);
	this.bottomRight = new FPoint(x2, y2);
    }

    public FRectangle(float x, float y, float width, float height) {
	this.x = Math.min(x, x + width);
	this.x2 = Math.max(x, x + width) - 1;
	this.y = Math.min(y, y + height);
	this.y2 = Math.max(y, y + height) - 1;

	this.width = width;
	this.height = height;

	this.size = new FDimension(width, height);

	this.topLeft = new FPoint(x, y);
	this.topRight = new FPoint(x2, y);
	this.bottomLeft = new FPoint(x, y2);
	this.bottomRight = new FPoint(x2, y2);
    }

    public FRectangle move(float x, float y) {
	return new FRectangle(this.x + x, this.y + y, width, height);
    }

    public FRectangle crop(FRectangle r) {
	return intersection(r);
    }
    
    public FRectangle reduce(FInsets insets) {
	return new FRectangle(new FPoint(x + insets.left, y + insets.top), new FPoint(x2 - insets.right, y2 - insets.bottom));
    }
    
    public FRectangle expand(FInsets insets) {
	return new FRectangle(new FPoint(x - insets.left, y - insets.top), new FPoint(x2 + insets.right, y2 + insets.bottom));
    }

    public FRectangle moveTo(float x, float y) {
	return new FRectangle(x, y, width, height);
    }

    public FRectangle rotate() {
	return new FRectangle(x, y, height, width);
    }

    public FRectangle scale(float scale) {
	return new FRectangle(x * scale, y * scale, x2 * scale, y2 * scale);
    }

    public FRectangle scale(float scaleX, float scaleY) {
	return new FRectangle(x * scaleX, y * scaleY, x2 * scaleX, y2 * scaleY);
    }

    public boolean contains(float x, float y) {
	return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
    }

    public boolean intersects(FRectangle r) {
	if ((x >= r.x2) || (x2 <= r.x) || (y >= r.y2) || (y2 <= r.y)) {
	    return false;
	}
	return true;
    }

    public FRectangle intersection(FRectangle r) {
	float _x1 = Math.max(x, r.x);
	float _x2 = Math.min(x2, r.x2);
	float _y1 = Math.max(y, r.y);
	float _y2 = Math.min(y2, r.y2);
	if (intersects(r)) {
	    return new FRectangle(new FPoint(_x1, _y1), new FPoint(_x2, _y2));
	}
	return new FRectangle(_x1, _y1, Math.max(0, _x2 - _x1 + 1), Math.max(0, _y2 - _y1 + 1));
    }

    public FRectangle translate(int tx, int ty) {
	return new FRectangle(tx + x, ty + y, width, height);
    }

    public FRectangle union(FRectangle r) {
	float _x1 = Math.min(x, r.x);
	float _x2 = Math.max(x2, r.x2);
	float _y1 = Math.min(y, r.y);
	float _y2 = Math.max(y2, r.y2);
	return new FRectangle(_x1, _y1, _x2 - _x1 + 1, _y2 - _y1 + 1);
    }

    public boolean contains2(int x, int y) {
	return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
    }

    public int hashCode() {
	float hash = 5;
	hash = 83 * hash + this.x;
	hash = 83 * hash + this.y;
	hash = 83 * hash + this.width;
	hash = 83 * hash + this.height;
	return (int) hash;
    }

    public boolean equals(Object obj) {
	if (obj != null && obj instanceof FRectangle) {
	    FRectangle r = (FRectangle) obj;
	    return (x == r.x) && (y == r.y) && (x2 == r.x2) && (y2 == r.y2);
	}
	return false;
    }

    public String toString() {
	return "FRectangle: " + x + ", " + y + ", " + width + ", " + height;
    }

    public IRectangle getIntegerInstance() {
	if (rect == null) {
	    IPoint p1 = new IPoint(Math.round(x), Math.round(y));
	    IPoint p2 = new IPoint(Math.round(x2), Math.round(y2));
	    rect = new IRectangle(p1, p2);
	    rect.frect = this;
	}
	return rect;
    }
    
    public DRectangle getDoubleInstance() {
	if(drect == null) {
	    DPoint p1  = new DPoint(x, y);
	    DPoint p2  = new DPoint(x2, y2);
	    drect = new DRectangle(p1, p2);
	    drect.frect = this;
	}
	return drect;
    }
}

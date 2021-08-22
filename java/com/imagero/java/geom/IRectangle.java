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
@SuppressWarnings("unused")
public class IRectangle {

	public final int x;
	public final int x2;
	public final int y;
	public final int y2;
	public final int width;
	public final int height;

	public final IDimension size;

	public final IPoint topLeft;
	public final IPoint topRight;
	public final IPoint bottomLeft;
	public final IPoint bottomRight;

	FRectangle frect;
	DRectangle drect;

	@SuppressWarnings("CopyConstructorMissesField")
	public IRectangle(IRectangle r) {
		this(r.x, r.y, r.width, r.height);
	}

	public IRectangle(IPoint p1, IPoint p2) {
		this.x = Math.min(p1.x, p2.x);
		this.y = Math.min(p1.y, p2.y);
		this.x2 = Math.max(p1.x, p2.x);
		this.y2 = Math.max(p1.y, p2.y);

		this.width = this.x2 - this.x + 1;
		this.height = this.y2 - this.y + 1;

		this.size = new IDimension(width, height);

		this.topLeft = new IPoint(x, y);
		this.topRight = new IPoint(x2, y);
		this.bottomLeft = new IPoint(x, y2);
		this.bottomRight = new IPoint(x2, y2);
	}

	public IRectangle(int x, int y, int width, int height) {
		this.x = Math.min(x, x + width);
		this.x2 = Math.max(x, x + width) - 1;
		this.y = Math.min(y, y + height);
		this.y2 = Math.max(y, y + height) - 1;

		this.width = width;
		this.height = height;

		this.size = new IDimension(width, height);

		this.topLeft = new IPoint(x, y);
		this.topRight = new IPoint(x2, y);
		this.bottomLeft = new IPoint(x, y2);
		this.bottomRight = new IPoint(x2, y2);
	}

	public IRectangle move(int x, int y) {
		return new IRectangle(this.x + x, this.y + y, width, height);
	}

	public IRectangle crop(IRectangle r) {
		return intersection(r);
	}

	public IRectangle reduce(Insets insets) {
		return new IRectangle(new IPoint(x + insets.left, y + insets.top), new IPoint(x2 - insets.right, y2 - insets.bottom));
	}

	public IRectangle expand(Insets insets) {
		return new IRectangle(new IPoint(x - insets.left, y - insets.top), new IPoint(x2 + insets.right, y2 + insets.bottom));
	}

	public IRectangle moveTo(int x, int y) {
		return new IRectangle(x, y, width, height);
	}

	public IRectangle rotate() {
		//noinspection SuspiciousNameCombination
		return new IRectangle(x, y, height, width);
	}

	public IRectangle scale(float scale) {
		return new IRectangle(Math.round(x * scale), Math.round(y * scale), Math.round(x2 * scale), Math.round(y2 * scale));
	}

	public IRectangle scale(float scaleX, float scaleY) {
		return new IRectangle(Math.round(x * scaleX), Math.round(y * scaleY), Math.round(x2 * scaleX), Math.round(y2 * scaleY));
	}

	public boolean contains(int x, int y) {
		return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
	}

	public boolean intersects(IRectangle r) {
		return (x < r.x2) && (x2 > r.x) && (y < r.y2) && (y2 > r.y);
	}

	public IRectangle intersection(IRectangle r) {
		int _x1 = Math.max(x, r.x);
		int _x2 = Math.min(x2, r.x2);
		int _y1 = Math.max(y, r.y);
		int _y2 = Math.min(y2, r.y2);
		if (intersects(r)) {
			return new IRectangle(new IPoint(_x1, _y1), new IPoint(_x2, _y2));
		}
		return new IRectangle(_x1, _y1, Math.max(0, _x2 - _x1 + 1), Math.max(0, _y2 - _y1 + 1));
	}

	public IRectangle translate(int tx, int ty) {
		return new IRectangle(tx + x, ty + y, width, height);
	}

	public IRectangle union(IRectangle r) {
		int _x1 = Math.min(x, r.x);
		int _x2 = Math.max(x2, r.x2);
		int _y1 = Math.min(y, r.y);
		int _y2 = Math.max(y2, r.y2);
		return new IRectangle(_x1, _y1, _x2 - _x1 + 1, _y2 - _y1 + 1);
	}

	public boolean contains2(int x, int y) {
		return (this.x <= x) && (this.y <= y) && (x2 >= x) && (y2 >= y);
	}

	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + this.x;
		hash = 83 * hash + this.y;
		hash = 83 * hash + this.width;
		hash = 83 * hash + this.height;
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj instanceof IRectangle) {
			IRectangle r = (IRectangle) obj;
			return (x == r.x) && (y == r.y) && (x2 == r.x2) && (y2 == r.y2);
		}
		return false;
	}

	public String toString() {
		return "Rectangle: " + x + ", " + y + ", " + width + ", " + height;
	}

	public FRectangle getFloatInstance() {
		if (frect == null) {
			if (drect != null) {
				if (drect.frect != null) {
					frect = drect.frect;
				} else {
					FPoint p1 = drect.topLeft.getFloatInstance();
					FPoint p2 = drect.bottomRight.getFloatInstance();
					frect = new FRectangle(p1, p2);
				}
			} else {
				FPoint p1 = new FPoint(x, y);
				FPoint p2 = new FPoint(x2, y2);
				frect = new FRectangle(p1, p2);
			}
			frect.rect = this;
		}
		return frect;
	}

	public DRectangle getDoubleInstance() {
		if (drect == null) {
			if (frect != null) {
				if (frect.drect != null) {
					drect = frect.drect;
				} else {
					DPoint p1 = frect.topLeft.getDoubleInstance();
					DPoint p2 = frect.bottomRight.getDoubleInstance();
					drect = new DRectangle(p1, p2);
				}
			} else {
				DPoint p1 = new DPoint(x, y);
				DPoint p2 = new DPoint(x2, y2);
				drect = new DRectangle(p1, p2);
			}
			drect.irect = this;
		}
		return drect;
	}
}

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
 *  o Neither the name of Andrey Kuznetsov nor the names of
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

import java.util.Vector;
import java.util.logging.Logger;

/**
 * Every line (in 2D) can be represented with formula y = a*x + b (exceptions -
 * horizontal [y=b] and vertical [x = n] lines);<br>
 * Class Line is based on this insight, this makes him very simple and clear.
 */
public class Line {

	float a;
	float b;

	float kx;
	float ky;

	boolean vertical, horizontal;

	public Line(Line l) {
		this.a = l.a;
		this.b = l.b;
	}

	public Line(float a, float b) {
		this.a = a;
		this.b = b;
	}

	public Line(IPoint p1, IPoint p2) {
		if (p1.equals(p2)) {
			throw new IllegalArgumentException();
		}
		if (p1.y == p2.y) {
			horizontal = true;
			ky = p1.y;
		} else if (p1.x == p2.x) {
			vertical = true;
			kx = p1.x;
		} else {
			horizontal = false;
			vertical = false;
			a = (p2.y - p1.y) / (float) (p2.x - p1.x);
			b = p1.y - a * p1.x;
		}
	}

	public double getAngle() {
		double x0 = 0;
		double x1 = 10;
		double y0 = a * x0 + b;
		double y1 = a * x1 + b;

		return Math.atan2(y1 - y0, x1 - x0);
	}

	/**
	 * determine if given point lies on this line
	 * 
	 * @param p
	 *            IPoint
	 * @return true if IPoint p is on this line
	 */
	public boolean contains(IPoint p) {
		if (horizontal) {
			if (p.y == ky) {
				return true;
			}
			return false;
		}
		if (vertical) {
			if (p.x == kx) {
				return true;
			}
			return false;
		}
		if (a * p.x + b == p.y) {
			return true;
		}
		return false;
	}

	public boolean contains(IPoint p, double round) {
		if (horizontal) {
			if (Math.abs(p.y - ky) < round) {
				return true;
			}
			return false;
		}
		if (vertical) {
			if (Math.abs(p.x - kx) < round) {
				return true;
			}
		}
		if (Math.abs(a * p.x + b - p.y) <= round) {
			return true;
		}
		return false;
	}

	/**
	 * get intersection point of this line with another one
	 * 
	 * @param ln
	 *            another line
	 * @return IPoint
	 */
	public final IPoint intersection(Line ln) {
		if ((horizontal && ln.horizontal) || (vertical && ln.vertical)) {
			return null;
		} else if (horizontal) {
			int y = (int) ky;
			int x;
			if (ln.vertical) {
				x = (int) ln.kx;
			} else {
				x = (int) ln.getX(y);// (int) ((y - ln.b) / ln.a);
			}
			return new IPoint(x, y);
		} else if (vertical) {
			int x = (int) kx;
			int y;
			if (ln.horizontal) {
				y = (int) ln.ky;
			}
			y = (int) ln.getY(x);
			return new IPoint(x, y);
		} else {
			int x = (int) ((ln.b - b) / (a - ln.a));
			int y = (int) (a * x + b);
			return new IPoint(x, y);
		}
	}

	public boolean intersects(IRectangle r) {
		LineSegment ls = new LineSegment(new IPoint(r.x, r.y), new IPoint(r.x, r.y2));
		if (intersects(ls)) {
			return true;
		}
		ls = new LineSegment(new IPoint(r.x2, r.y), new IPoint(r.x2, r.y2));
		if (intersects(ls)) {
			return true;
		}
		ls = new LineSegment(new IPoint(r.x, r.y), new IPoint(r.x2, r.y));
		if (intersects(ls)) {
			return true;
		}
		ls = new LineSegment(new IPoint(r.x, r.y2), new IPoint(r.x2, r.y2));
		if (intersects(ls)) {
			return true;
		}
		return false;
	}

	public LineSegment crop(IRectangle r) {
		LineSegment left = new LineSegment(new IPoint(r.x, r.y), new IPoint(r.x, r.y2));
		LineSegment right = new LineSegment(new IPoint(r.x2, r.y), new IPoint(r.x2, r.y2));
		LineSegment top = new LineSegment(new IPoint(r.x, r.y), new IPoint(r.x2, r.y));
		LineSegment bottom = new LineSegment(new IPoint(r.x, r.y2), new IPoint(r.x2, r.y2));

		Vector<IPoint> v = new Vector<IPoint>();

		IPoint p = intersection(left);
		if (p != null) {
			v.add(p);
		}
		p = intersection(right);
		if (p != null) {
			v.add(p);
		}
		p = intersection(top);
		if (p != null) {
			v.add(p);
		}
		p = intersection(bottom);
		if (p != null) {
			v.add(p);
		}
		if (v.size() < 2) {
			Logger.getLogger(getClass().getName()).warning("not anough points at crop");
			return null;
		}
		if (v.size() > 2) {
			Logger.getLogger(getClass().getName()).warning("too much points at crop");
			Vector<IPoint> v2 = new Vector<IPoint>();
			v2.add(v.remove(0));
			while (v.size() > 0) {
				p = v.remove(0);
				if (!v2.contains(p)) {
					v2.add(p);
				}
			}
			v = v2;
		}
		return new LineSegment(v.remove(0), v.remove(0));
	}

	/**
	 * get x coordinate of point on line by given y coordinate
	 * 
	 * @param y
	 *            y coordinate
	 * @return x coordinate
	 */
	public float getX(float y) {
		if (horizontal) {
			return kx;
		}
		return ((y - b) / a);
	}

	/**
	 * get y coordinate of point on line by given x coordinate
	 * 
	 * @param x
	 *            x coordinate
	 * @return
	 */
	public float getY(float x) {
		if (horizontal) {
			return ky;
		}
		return a * x + b;
	}

	/**
	 * find nearest point on this Line to supplied IPoint
	 * 
	 * @param p
	 *            IPoint
	 * @return nearest IPoint
	 */
	public IPoint nearestPoint(IPoint p) {
		if (vertical) {
			return new IPoint((int) kx, p.y);
		} else if (horizontal) {
			return new IPoint(p.x, (int) ky);
		} else {
			float a2 = -1.0f / a;
			float b2 = p.y - a2 * p.x;
			return intersection(new Line(a2, b2));
		}
	}

	public boolean intersects(LineSegment ls) {
		IPoint p = intersection(ls);
		return p != null && ls.contains(p);
	}

	public boolean isOver(IPoint p) {
		return a * p.x + b < p.y;
	}

	public boolean isUnder(IPoint p) {
		return a * p.x + b > p.y;
	}
}

/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * http://jgui.imagero.com
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

/**
 * LineSegment is like a Line, but bounded through 2 Points
 */
public class LineSegment extends Line {

    public final IPoint p1, p2;

    public LineSegment(LineSegment ls) {
	super(ls);
	this.p1 = ls.p1;
	this.p2 = ls.p2;
    }

    public LineSegment(IPoint p1, IPoint p2) {
	super(p1, p2);
	this.p1 = p1;
	this.p2 = p2;
    }

    public boolean contains(IPoint p) {
	int minX = p1.x, maxX = p2.x;
	int minY = p1.y, maxY = p2.y;
	if (p1.x >= p2.x) {
	    minX = p2.x;
	    maxX = p1.x;
	}
	if (p1.y >= p2.y) {
	    minY = p2.y;
	    maxY = p1.y;
	}
	boolean b0 = (p.x >= minX && p.x <= maxX) && (p.y >= minY && p.y <= maxY);
	return b0 && super.contains(p);
    }

    public boolean contains(IPoint p, double round) {
	int minX = p1.x, maxX = p2.x;
	int minY = p1.y, maxY = p2.y;
	if (p1.x >= p2.x) {
	    minX = p2.x;
	    maxX = p1.x;
	}
	if (p1.y >= p2.y) {
	    minY = p2.y;
	    maxY = p1.y;
	}

	boolean b0 = (p.x >= minX && p.x <= maxX) && (p.y >= minY && p.y <= maxY);
	return b0 && super.contains(p, round);
    }

    public boolean intersects(LineSegment ls) {
	IPoint p = intersection(ls);
	return ls.contains(p) && this.contains(p);
    }

    public IPoint nearestPoint(IPoint p) {
	IPoint np = super.nearestPoint(p);
	int minX = p1.x, maxX = p2.x;
	int minY = p1.y, maxY = p2.y;
	if (p1.x >= p2.x) {
	    minX = p2.x;
	    maxX = p1.x;
	}
	if (p1.y >= p2.y) {
	    minY = p2.y;
	    maxY = p1.y;
	}

	boolean b0 = (np.x >= minX && np.x <= maxX) && (np.y >= minY && np.y <= maxY);

	if (b0) {
	    return np;
	}
	int dx1 = (p.x - p1.x) * (p.x - p1.x) + (p.y - p1.y) * (p.y - p1.y);
	int dx2 = (p.x - p2.x) * (p.x - p2.x) + (p.y - p2.y) * (p.y - p2.y);
	if (dx1 < dx2) {
	    return p1;
	}
	return p2;
    }

    public String toString() {
	return p1.toString() + " " + p2.toString();
    }
}

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
public final class FPoint {
    public final float x;
    public final float y;
    IPoint ipoint;
    DPoint dpoint;

    public FPoint() {
        x = 0;
        y = 0;
    }

    public FPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof FPoint) {
            FPoint p = (FPoint) obj;
            return (x == p.x) && (y == p.y);
        }
        return false;
    }

    public int hashCode() {
	float hash = 5;
        hash = 83 * hash + this.x;
        hash = 83 * hash + this.y;
        return (int) hash;
    }

    public String toString() {
        return "FPoint: (" + x + ", " + y + ")";
    }
    
    public IPoint getIntegerInstance() {
	if(ipoint == null) {
	    ipoint = new IPoint(Math.round(x), Math.round(y));
	    ipoint.fpoint = this;
	}
	return ipoint;
    }
    
    public DPoint getDoubleInstance() {
	if(dpoint == null) {
	    dpoint = new DPoint(x, y);
	    dpoint.fpoint = this;
	}
	return dpoint;
    }


}

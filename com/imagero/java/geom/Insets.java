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
 * Date: 24.10.2008
 * 
 * @author Andrey Kuznetsov
 */
public final class Insets {
    public final int top;
    public final int left;
    public final int bottom;
    public final int right;
    public final int width;
    public final int height;

    FInsets ins;

    public Insets(int top, int left, int bottom, int right) {
	this.top = top;
	this.left = left;
	this.bottom = bottom;
	this.right = right;
	width = right + left;
	height = top + bottom;
    }

    public boolean equals(Object obj) {
	if (obj != null && obj instanceof Insets) {
	    Insets insets = (Insets) obj;
	    return (top == insets.top) && (left == insets.left) && (right == insets.right) && (bottom == insets.bottom);
	}
	return false;
    }

    public int hashCode() {
	int hash = 5;
	hash = 83 * hash + top;
	hash = 83 * hash + left;
	hash = 83 * hash + bottom;
	hash = 83 * hash + right;
	return hash;
    }

    public Insets add(Insets insets) {
	return new Insets(top + insets.top, left + insets.left, bottom + insets.bottom, right + insets.right);
    }

    public FInsets getFloatInstance() {
	if (ins == null) {
	    ins = new FInsets(top, left, bottom, right);
	}
	return ins;
    }
}

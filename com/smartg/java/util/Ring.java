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

package com.smartg.java.util;

import java.util.EmptyStackException;

/**
 * Ring implementation.
 */
public class Ring<E> {

    E[] elements;

    int start;
    int count;

    final int max;

    public Ring() {
	this(10);
    }

    /**
     * create new Ring object
     * 
     * @param size
     *            Ring size
     */
    @SuppressWarnings("unchecked")
    public Ring(int size) {
	this.elements = (E[]) new Object[size * 2];
	start = elements.length;
	max = size;
    }

    public E add(E item) {
	if (start > 0) {
	    elements[--start] = item;
	    if (count < max) {
		count++;
	    }
	} else {
	    System.arraycopy(elements, 0, elements, max, max);
	    start = max;
	    elements[--start] = item;
	}
	return elements[start + count - 1];
    }

    public E get(int index) {
	if (count > 0) {
	    if (index >= 0 && index < count) {
		return elements[start + index];
	    }
	    throw new IndexOutOfBoundsException();
	}
	throw new EmptyStackException();
    }

    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < getCount(); i++) {
	    sb.insert(0, String.valueOf(get(i)));
	}
	return sb.toString();
    }

    public int getCount() {
	return count;
    }

    public boolean isEmpty() {
	return count == 0;
    }
}

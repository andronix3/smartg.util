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

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Allows to filter values of Enumeration.
 *
 * @param <E>
 */
public abstract class FilteredEnumeration<E> implements Enumeration<E>, Iterator<E>, IFilter<E> {

    private final Enumeration<E> e;

    public boolean hasNext() {
	return next != null;
    }

    public E next() {
	E nxt = next;
	next = getNext(e);
	return nxt;
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }

    private E next;

    public FilteredEnumeration(Enumeration<E> e) {
	this.e = e;
	next = getNext(e);
    }

    private E getNext(Enumeration<E> e) {
	while (e.hasMoreElements()) {
	    E obj = e.nextElement();
	    if (obj != null && accept(obj)) {
		return obj;
	    }
	}
	return null;
    }

    public boolean hasMoreElements() {
	return next != null;
    }

    public E nextElement() {
	E nxt = next;
	next = getNext(e);
	return nxt;
    }

    public abstract boolean accept(E obj);
}

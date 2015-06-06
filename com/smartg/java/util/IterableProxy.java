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
 * IterableProxy. Safe Wrap Iterable (optional - immutable) or make Iterable
 * from Iterator or Enumeration (will work only one time per Iterator/Enumeration).
 */
public class IterableProxy<E> implements Iterable<E> {
    private Iterable<E> iterable;
    private boolean immutable;

    private Iterator<E> iterator;
    private boolean iteratorUsed;

    private Enumeration<E> enumeration;

    public IterableProxy(Iterable<E> iterable) {
	if(iterable == null) {
	    throw new NullPointerException();
	}
	this.iterable = iterable;
    }

    public IterableProxy(Iterable<E> iterable, boolean immutable) {
	if(iterable == null) {
	    throw new NullPointerException();
	}
	this.iterable = iterable;
	this.immutable = immutable;
    }

    public IterableProxy(Iterator<E> iterator) {
	if(iterator == null) {
	    throw new NullPointerException();
	}
	this.iterator = iterator;
    }

    public IterableProxy(Iterator<E> iterator, boolean immutable) {
	if(iterator == null) {
	    throw new NullPointerException();
	}
	this.iterator = iterator;
	this.immutable = immutable;
    }

    public IterableProxy(Enumeration<E> enumeration) {
	if(enumeration == null) {
	    throw new NullPointerException();
	}
	this.enumeration = enumeration;
    }

    public Iterator<E> iterator() {
	if (iterable != null) {
	    if (immutable) {
		return new SafeIterator<E>(iterable.iterator());
	    }
	    return iterable.iterator();
	} else if (iterator != null && !iteratorUsed) {
	    iteratorUsed = true;
	    if (immutable) {
		return new SafeIterator<E>(iterator);
	    }
	    return iterator;
	} else if (enumeration != null && !iteratorUsed) {
	    iteratorUsed = true;
	    return new SafeEnumeration<E>(enumeration);
	}
	return null;
    }
}

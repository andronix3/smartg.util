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
 * Simple array enumeration.
 * Do we really need this?
 * Hmm... Good question. We can surely do it without this class, but using this class wie can make it shorter:
 * 
 * Example:
 * 
 * 
 * <code>
 * public Enumeration<String> names() throws IOException {
 *	return new SafeEnumerationWrapper<String>(Arrays.asList(list()).iterator());
 * }
 * 
 *  public Enumeration<String> names() throws IOException {
 * 	return new ArrayEnumeration<String>(list());
 *  }
 *  
 *  </code>
 * @author andrey
 *
 * @param <E>
 */
public class ArrayIterator<E> implements Enumeration<E>, Iterator<E> {

    private final E[] array;
    
    private int index;
    private E next;

    public ArrayIterator(E[] array) {
	this.array = array;
	goNext();
    }

    public boolean hasMoreElements() {
	return next != null;
    }
    
    private void goNext() {
	while (index < array.length && next == null) {
	    next = array[index++];
	}
    }

    public E nextElement() {
	E tmp = next;
	next = null;
	goNext();
	return tmp;
    }

    public boolean hasNext() {
	return next != null;
    }

    public E next() {
	E tmp = next;
	next = null;
	goNext();
	return tmp;
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }
}

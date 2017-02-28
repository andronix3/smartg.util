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

 package com.imagero.java.util;



/**
 * OpenVector is rather an array with some check and management functions.
 * Use on own risk!
 */
public class OpenVector {

    private Object[] elements;

    int startSize;


    public OpenVector() {
        this(3);
    }

    public OpenVector(int size) {
        this.startSize = size;
        this.elements = create();
    }

    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != null && elements[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            elements[index] = null;
        }
        return index >= 0;
    }

    public Object [] checkSize(int minSize) {
        if (minSize > elements.length) {
            Object oldData[] = elements;
            elements = create((int) Math.max(minSize + 10, elements.length + Math.sqrt(elements.length)));
            System.arraycopy(oldData, 0, elements, 0, elements.length);
        }
        return elements;
    }

    public int getCount(Class<?> c) {
        int count = 0;
        for (int i = 0; i < elements.length; i++) {
            if (c.isInstance(elements[i])) {
                count++;
            }
        }
        return count;
    }

    /**
     * OpenVector allows direct access to its elements array.
     * Use on own risc - e.g. use it only for reading, don't change anything in this array!
     */
    public Object[] getElements() {
        return elements;
    }

    public Object [] clear() {
        elements = create();
        return elements;
    }

    protected Object[] create() {
        return new Object[startSize];
    }

    protected Object[] create(int size) {
        return new Object[size];
    }
}

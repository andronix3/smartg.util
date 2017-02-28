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

 package com.imagero.reader.tools;

import java.util.EmptyStackException;

/**
 *
 * Firt-In-First-Out implementation.
 *
 * @author Andrey Kuznetsov
 */
public class Fifo<E> {

    protected Object[] elements;

    protected int readPos;
    protected int writePos;

//    private int threshold = 10;

    public Fifo() {
        this(10);
    }

    public Fifo(int count) {
        this.elements = new Object[count];
    }

    public void push(E o) {
        checkWritePos();
        elements[writePos++] = o;
    }

    public int size() {
        return writePos - readPos;
    }

    protected void checkWritePos() {
        if(writePos >= elements.length) {
            int length = size();
            if(readPos > 0) {
                System.arraycopy(elements, readPos, elements, 0, length);
                readPos = 0;
                int wp = writePos;
                writePos = length;
                for (int i = writePos; i < wp; i++) {
                    elements[i] = null;
                }
            }
            else {
                int nl = elements.length + (int) Math.min(4, Math.log(elements.length));
                Object [] tmp = new Object[nl];
                System.arraycopy(elements, 0, tmp, 0, length);
                elements = tmp;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if(readPos < writePos) {
            return (E) elements[readPos++];
        }
	throw new EmptyStackException();
    }
}

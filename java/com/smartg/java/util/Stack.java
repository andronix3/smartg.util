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
import java.util.Enumeration;

/**
 * Stack implementation NOT based on Vector
 */
public class Stack<E> {

	private Object[] elements;

	int count;

	private Stack(Stack<E> stack) {
		this.elements = stack.elements.clone();
		this.count = stack.count;
	}

	public Stack() {
		this(10);
	}

	public Stack(int count) {
		this.elements = new Object[count];
	}

	public E push(E item) {
		checkSize(count + 1);
		elements[count++] = item;
		return item;
	}

	@SuppressWarnings("unchecked")
	public E pop() {
		if (count > 0) {
			count--;
			E obj = (E) elements[count];
			elements[count] = null;
			return obj;
		}
		throw new EmptyStackException();
	}

	public E peek() {
		return peek(0);
	}

	@SuppressWarnings("unchecked")
	public E peek(int index) {
		int ind_x = count - 1 - index;
		if (ind_x >= count || ind_x < 0) {
			throw new IndexOutOfBoundsException();
		}
		return (E) elements[ind_x];
	}

	public boolean canPeek(int index) {
		int ind_x = count - 1 - index;
		return ind_x < count && ind_x >= 0;
	}

	public int getCount() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	private void checkSize(int minSize) {
		if (minSize > elements.length) {
			Object[] oldData = elements;
			elements = new Object[Math.max(minSize + 10, elements.length * 2)];
			System.arraycopy(oldData, 0, elements, 0, count);
		}
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Enumeration<E> en = elements(false);
		if (en.hasMoreElements()) {
			sb.append(en.nextElement().toString());
		}
		while (en.hasMoreElements()) {
			sb.append(",");
			sb.append(en.nextElement().toString());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * swap two top elements.
	 *
	 * @return true on success
	 */
	public boolean swap() {
		if (count > 1) {
			int index1 = count - 1;
			int index2 = count - 2;
			Object e1 = elements[index1];
			elements[index1] = elements[index2];
			elements[index2] = e1;
			return true;
		}
		return false;
	}

	/**
	 * create new Stack with same elements in reversed order
	 *
	 * @return Stack
	 */
	public Stack<E> reverse() {
		Stack<E> stack = new Stack<>(count);
		for (int i = 0; i < count; i++) {
			stack.push(peek(i));
		}
		return stack;
	}

	public Enumeration<E> elements(boolean reverse) {
		if (reverse) {
			return new StackEnumerator<>(reverse());
		}
		return new StackEnumerator<>(new Stack<>(this));
	}

	static class StackEnumerator<E> implements Enumeration<E> {

		Stack<E> stack;

		private StackEnumerator(Stack<E> stack) {
			this.stack = stack;
		}

		public boolean hasMoreElements() {
			return !stack.isEmpty();
		}

		public E nextElement() {
			return stack.pop();
		}
	}
}

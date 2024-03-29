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

/**
 * Imagine you have class C, which implements Enumeration, class B which gives
 * you Enumeration of Objects of type C and class A which enumerates over
 * Objects of type B. With IEnumerator you may enumerate them all.
 * 
 * <pre>
 * class C implements Enumeration {
 * }
 * 
 * class B implements Enumeration<C>; {
 * }
 * 
 * class A implements Enumeration<B>; {
 * }
 * </pre>
 * 
 * @author andrey
 * 
 */
public class IEnumerator<K, E> implements Enumeration<E> {

	Enumeration<E> current;
	E next;

	protected final Stack<Enumeration<E>> stack = new Stack<>();

	protected IFilter<E> filter;

	public IEnumerator(EnumerableEx<K, E> en) {
		this(en, null);
	}

	public IEnumerator(EnumerableEx<K, E> en, IFilter<E> filter) {
		this.filter = filter;
		current = en.enumeration();
		moveNext();
	}

	public IEnumerator(Enumeration<E> en) {
		this(en, null);
	}

	public IEnumerator(Enumeration<E> en, IFilter<E> filter) {
		this.filter = filter;
		current = en;
		moveNext();
	}

	@SuppressWarnings("unchecked")
	private boolean moveNext0() {
		if (current != null && current.hasMoreElements()) {
			E nextElement = current.nextElement();

			if (nextElement instanceof EnumerableEx<?, ?>) {
				stack.push(current);
				EnumerableEx<K, E> e = (EnumerableEx<K, E>) nextElement;
				current = e.enumeration();
				return askFilter(nextElement);
			} else if (nextElement instanceof Enumeration<?>) {
				stack.push(current);
				current = (Enumeration<E>) nextElement;
				return askFilter(nextElement);
			} else {
				if (filter != null) {
					return askFilter(nextElement);
				}
				next = nextElement;
				return true;
			}
		}
		if (!stack.isEmpty()) {
			current = stack.pop();
			return false;
		}
		return true;
	}

	private boolean askFilter(E nextElement) {
		boolean accepted = filter == null || filter.accept(nextElement);
		if (accepted) {
			next = nextElement;
			return true;
		}
		return false;
	}

	synchronized void moveNext() {
		boolean res = false;
		while (!res) {
			res = moveNext0();
		}
	}

	public boolean hasMoreElements() {
		return next != null;
	}

	public E nextElement() {
		E res = next;
		next = null;
		moveNext();
		return res;
	}
}

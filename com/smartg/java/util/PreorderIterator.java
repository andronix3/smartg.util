package com.smartg.java.util;

import java.util.Iterator;
import java.util.Stack;

public class PreorderIterator<T extends Iterable<T>> implements Iterator<T> {
	private final Stack<Iterator<T>> stack = new Stack<Iterator<T>>();

	public PreorderIterator(T node) {
		stack.push(node.iterator());
	}

	public boolean hasNext() {
		return (!stack.empty() && stack.peek().hasNext());
	}

	public T next() {
		Iterator<T> iterator = stack.peek();
		T node = iterator.next();
		Iterator<T> children = node.iterator();

		if (!iterator.hasNext()) {
			stack.pop();
		}
		if (children.hasNext()) {
			stack.push(children);
		}
		return node;
	}
}
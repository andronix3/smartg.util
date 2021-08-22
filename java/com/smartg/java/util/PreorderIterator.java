package com.smartg.java.util;

import java.util.Iterator;
import java.util.Stack;

public class PreorderIterator<T extends Iterable<T>> implements Iterator<T> {
	private final Stack<Iterator<T>> stack = new Stack<>();

	public PreorderIterator(T node) {
		stack.push(node.iterator());
	}

	@Override
	public boolean hasNext() {
		return (!stack.empty() && stack.peek().hasNext());
	}

	@Override
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
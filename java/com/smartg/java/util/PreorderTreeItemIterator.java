package com.smartg.java.util;

import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.control.TreeItem;

public class PreorderTreeItemIterator<T extends TreeItem<N>, N> implements Iterator<TreeItem<N>> {
	private final Stack<Iterator<TreeItem<N>>> stack = new Stack<>();

	public PreorderTreeItemIterator(T node) {
		stack.push(new TreeItemIterator<>(node));
	}

	@Override
	public boolean hasNext() {
		return (!stack.empty() && stack.peek().hasNext());
	}

	@Override
	public TreeItem<N> next() {
		Iterator<TreeItem<N>> iterator = stack.peek();
		TreeItem<N> node = iterator.next();
		Iterator<TreeItem<N>> children = new TreeItemIterator<>(node);

		if (!iterator.hasNext()) {
			stack.pop();
		}
		if (children.hasNext()) {
			stack.push(children);
		}
		return node;
	}

	static class TreeItemIterator<N> implements Iterator<TreeItem<N>> {
		private TreeItem<N> node;
		private AtomicInteger index = new AtomicInteger(0);

		public TreeItemIterator(TreeItem<N> node) {
			this.node = node;
		}

		@Override
		public boolean hasNext() {
			return index.get() < node.getChildren().size();
		}

		@Override
		public TreeItem<N> next() {
			return node.getChildren().get(index.getAndIncrement());
		}
	}
}
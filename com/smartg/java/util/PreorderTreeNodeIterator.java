package com.smartg.java.util;

import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.tree.TreeNode;

public class PreorderTreeNodeIterator<T extends TreeNode> implements Iterator<TreeNode> {
	private final Stack<Iterator<TreeNode>> stack = new Stack<>();

	public PreorderTreeNodeIterator(T node) {
		stack.push(new TreeNodeIterator(node));
	}

	@Override
	public boolean hasNext() {
		return (!stack.empty() && stack.peek().hasNext());
	}

	@Override
	public TreeNode next() {
		Iterator<TreeNode> iterator = stack.peek();
		TreeNode node = iterator.next();
		Iterator<TreeNode> children = new TreeNodeIterator(node);

		if (!iterator.hasNext()) {
			stack.pop();
		}
		if (children.hasNext()) {
			stack.push(children);
		}
		return node;
	}
		
	static class TreeNodeIterator implements Iterator<TreeNode> {
		private TreeNode node;
		private AtomicInteger index = new AtomicInteger(0);
		
		public TreeNodeIterator(TreeNode node) {
			this.node = node;
		}
		
		@Override
		public boolean hasNext() {
			return index.get() < node.getChildCount();
		}

		@Override
		public TreeNode next() {
			return node.getChildAt(index.getAndIncrement());
		}
	}
}
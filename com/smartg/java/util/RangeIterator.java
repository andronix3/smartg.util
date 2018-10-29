package com.smartg.java.util;

import java.util.Iterator;

public class RangeIterator implements Iterator<Integer> {

	private int last;
	private int current;

	public RangeIterator(int first, int last) {
		this.last = last;
		this.current = first;
	}

	@Override
	public boolean hasNext() {
		return current < last;
	}

	@Override
	public Integer next() {
		return current++;
	}
}
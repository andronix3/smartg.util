package com.smartg.java.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CancelableIterator<E> implements Iterator<E> {

	private boolean canceled;
	private final Iterator<E> iterator;

	public CancelableIterator(Iterator<E> iterator) {
		this.iterator = Objects.requireNonNull(iterator);
	}

	public void cancel() {
		this.canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public boolean hasNext() {
		if (canceled) {
			return false;
		}
		return iterator.hasNext();
	}

	@Override
	public E next() {
		if (canceled) {
			throw new NoSuchElementException();
		}
		return iterator.next();
	}

}

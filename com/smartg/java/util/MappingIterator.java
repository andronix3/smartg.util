package com.smartg.java.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * Iterate and apply Function "on the fly"
 * @author User
 *
 * @param <T>
 * @param <E>
 */
public class MappingIterator<T, E> implements Iterator<E> {

	private Function<T, E> mapper;
	private Iterator<T> iterator;

	public MappingIterator(Function<T, E> mapper, Iterator<T> iterator) {
		this.mapper = Objects.requireNonNull(mapper);
		this.iterator = Objects.requireNonNull(iterator);
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public E next() {
		return mapper.apply(iterator.next());
	}

}

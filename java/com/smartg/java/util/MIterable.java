package com.smartg.java.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator items from multiple Iterators
 *
 * @author User
 *
 * @param <E>
 */
public class MIterable<E> implements Iterable<E> {

    private final List<Iterator<E>> list = new ArrayList<>();

    public void add(Iterable<E> iterable) {
        list.add(iterable.iterator());
    }
 
    public void add(Iterator<E> iterator) {
        list.add(iterator);
    }

    static class MIterator<E> implements Iterator<E> {

        private final List<Iterator<E>> list;
        private Iterator<Iterator<E>> iterator;
        private Iterator<E> current;

        public MIterator(List<Iterator<E>> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            Iterator<E> iter = getIterator();
            return iter != null && iter.hasNext();
        }

        private Iterator<E> getIterator() {
            if (current != null && current.hasNext()) {
                return current;
            }
            if (iterator == null) {
                iterator = list.iterator();
            }
            while (current == null || !current.hasNext()) {
                if (iterator.hasNext()) {
                    current = iterator.next();
                } else {
                    return null;
                }
            }

            return current;
        }

        @Override
        public E next() {
            Iterator<E> iter = getIterator();
            if (iter == null) {
                return null;
            }
            return iter.next();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MIterator<>(list);
    }
}

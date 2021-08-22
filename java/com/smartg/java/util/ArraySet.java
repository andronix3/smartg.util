package com.smartg.java.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author andro
 * @param <E>
 */
public final class ArraySet<E> extends ArrayList<E> implements Set<E> {

    private static final long serialVersionUID = -4224200640337474266L;

    public ArraySet() {
    }

    public ArraySet(int initialCapacity) {
        super(initialCapacity);
    }

    public ArraySet(Collection<? extends E> c) {
        addAll(filter(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        List<? extends E> collected = c.stream().filter(t -> !this.contains(t)).collect(Collectors.toList());
        ArrayList<E> list = filter(collected);
        return super.addAll(index, list);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        List<E> collected = c.stream().filter(t -> !this.contains(t)).collect(Collectors.toList());
        ArrayList<E> list = filter(collected);
        return super.addAll(list);
    }

    private ArrayList<E> filter(Collection<? extends E> collected) {
        //ensure that our collection contains only unique objects
        ArrayList<E> list = new ArrayList<>();
        collected.forEach(t -> {
            if (!list.contains(t)) {
                list.add(t);
            }
        });
        return list;
    }

    @Override
    public void add(int index, E element) {
        if (contains(element)) {
            return;
        }
        super.add(index, element);
    }

    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }
        return super.add(e);
    }
}

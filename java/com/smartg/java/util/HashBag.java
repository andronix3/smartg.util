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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * HashBag - multiple values for given key.
 */
@SuppressWarnings("unused")
public class HashBag<K, E> {
    private final HashMap<K, ArrayList<E>> hashtable = new HashMap<>();

    public int size() {
	return hashtable.size();
    }

    public boolean isEmpty() {
	return hashtable.isEmpty();
    }

    public Enumeration<K> keys() {
		return new SafeIterator<>(hashtable.keySet().iterator());
	}

    public E get(K key, int index) {
		ArrayList<E> list = hashtable.get(key);
		if (list != null) {
			return list.get(index);
		}
		return null;
	}

    public void put(K key, E value) {
		hashtable.computeIfAbsent(key, k -> new ArrayList<>(1)).add(value);
    }

    /**
     * removes the key and all corresponding values from HashBag
     * 
     * @param key key
     */
    public ArrayList<E> removeAll(K key) {
	ArrayList<E> list = hashtable.remove(key);
	if (list == null) {
	    return new ArrayList<>();
	}
	return list;
    }

    /**
     * remove Object from HashBag
     * 
     * @param key
     *            key to search
     * @param index
     *            index to remove
     * @return Object
     */
    public E remove(K key, int index) {
	ArrayList<E> list = hashtable.get(key);
	if (list != null) {
	    E o = list.get(index);
	    list.remove(index);
	    if (list.size() == 0) {
		hashtable.remove(key);
	    }
	    return o;
	}
	return null;
    }

    /**
     * remove Object from HashBag
     * 
     * @param key
     *            key to search
     * @param obj
     *            Object to remove
     * @return true if <code>obj</code> was found
     */
    public boolean remove(K key, E obj) {
	ArrayList<E> list = hashtable.get(key);
	if (list == null) {
	    return false;
	}
	boolean b = list.remove(obj);
	if (list.size() == 0) {
	    hashtable.remove(key);
	}
	return b;
    }
    
    public boolean removeValue(E o) {
		return findKey(o) != null;
	}


    /**
     * get count of objects correspondings to <code>key</code>
     * 
     * @param key key
     */
    public int getCount(K key) {
	ArrayList<E> list = hashtable.get(key);
	if (list != null) {
	    return list.size();
	}
	return 0;
    }

    /**
     * Returns true if HashBag contains the specified element
     * 
     * @param o
     *            object to be checked
     * @return true if the specified element is contained in HashBag.
     */
    public boolean contains(E o) {
		return findKey(o) != null;
	}

    private K findKey(E o) {
		Enumeration<K> e = new SafeIterator<>(hashtable.keySet().iterator());
		while (e.hasMoreElements()) {
			K key = e.nextElement();
			ArrayList<E> list = hashtable.get(key);
			// shouldn't happen, but who knows...
			if (list != null) {
				if (list.contains(o)) {
					return key;
				}
			}
		}
		return null;
	}

    /**
     * Returns true if HashBag contains the specified element under given key.
     * 
     * @param key
     *            key to search
     * @param o
     *            Object to search
     * @return true if specified element was found
     */
    public boolean contains(K key, E o) {
		ArrayList<E> list = hashtable.get(key);
		if (list == null) {
			return false;
		}
		return list.contains(o);
	}
}

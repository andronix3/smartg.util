package com.smartg.tokenizer;

import java.util.Arrays;

/**
 * String.subsequence(start, end) will create a copy of (a part) of the backing
 * char array. This class will copy array only once, so memory usage is constant
 * 
 * @author andro
 *
 */
public class MyCharSequence implements CharSequence {

	private char[] chars;
	private int start;
	private int end;

	public MyCharSequence(char[] chars) {
		this(chars, 0, chars.length);
	}

	public MyCharSequence(char[] chars, int offset) {
		this(chars, offset, chars.length - offset);
	}

	public MyCharSequence(char[] chars, int start, int end) {
		if (start < 0) {
			throw new IllegalArgumentException("Start must be positive: " + start);
		} else if (end < start) {
			throw new IllegalArgumentException("Start must be lesser as end: " + start + " " + end);
		} else if (end > chars.length) {
			throw new IllegalArgumentException("End must be lesser as length: " + end + " " + chars.length);
		}
		this.chars = chars;
		this.start = start;
		this.end = end;
	}

	@Override
	public int length() {
		return end - start;
	}

	@Override
	public char charAt(int index) {
		return chars[start + index];
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new MyCharSequence(chars, this.start + start, this.start + end);
	}

	private String toString;

	/**
	 * Lazy toString() creation
	 */
	@Override
	public String toString() {
		if (toString == null) {
			toString = new String(Arrays.copyOfRange(chars, start, end));
		}
		return toString;
	}

	public static void main(String[] args) {
		String str = "MyCharSequence";
		CharSequence s = str;
		CharSequence mcs = new MyCharSequence(str.toCharArray());
		s = s.subSequence(2, s.length());
		mcs = mcs.subSequence(2, mcs.length());
		System.out.println(s);
		System.out.println(mcs);

	}
}

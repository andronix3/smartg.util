package com.smartg.tokenizer;

/**
 * String.subsequence(start, end) will create a copy of (a part) of the backing
 * char array. This class omits entirely array copying, working direct with
 * String
 * 
 * @author andro
 *
 */
public class StringCharSequence implements CharSequence {

	private String chars;
	private int start;
	private int end;

	public StringCharSequence(String chars) {
		this(chars, 0, chars.length());
	}

	public StringCharSequence(String chars, int offset) {
		this(chars, offset, chars.length() - offset);
	}

	public StringCharSequence(String chars, int start, int end) {
		if (start < 0) {
			throw new IllegalArgumentException("Start must be positive: " + start);
		} else if (end < start) {
			throw new IllegalArgumentException("Start must be lesser as end: " + start + " " + end);
		} else if (end > chars.length()) {
			throw new IllegalArgumentException("End must be lesser as length: " + end + " " + chars.length());
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
		return chars.charAt(start + index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new StringCharSequence(chars, this.start + start, this.start + end);
	}

	private String toString;

	/**
	 * Lazy toString() creation
	 */
	@Override
	public String toString() {
		if (toString == null) {
			toString = chars.substring(start, end);
		}
		return toString;
	}

	public static void main(String[] args) {
		String str = "StringCharSequence";
		CharSequence s = str;
		CharSequence mcs = new StringCharSequence(str);
		s = s.subSequence(6, s.length());
		mcs = mcs.subSequence(6, mcs.length());
		System.out.println(s);
		System.out.println(mcs);

	}
}

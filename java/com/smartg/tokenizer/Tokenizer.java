package com.smartg.tokenizer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer<T> implements Iterable<Token<T>> {
	private LinkedList<TokenInfo<T>> tokenInfos;
	private LinkedList<Token<T>> tokens;

	public Tokenizer() {
		tokenInfos = new LinkedList<>();
		tokens = new LinkedList<>();
	}

	public void add(String regex, T token) {
		tokenInfos.add(new TokenInfo<>(Pattern.compile("^(" + regex + ")"), token));
	}

	public void tokenize(String str) {
		long time = System.currentTimeMillis();
		CharSequence s = new StringCharSequence(str);
		// CharSequence s = new MyCharSequence(str.toCharArray());
		tokens.clear();
		while (s.length() > 0) {
			boolean match = false;
			for (TokenInfo<T> info : tokenInfos) {
				Matcher m = info.regex.matcher(s);
				if (m.find()) {
					match = true;
					String t = m.group();
					s = s.subSequence(t.length(), s.length());
					tokens.add(new Token<>(info.token, t));
					break;
				}
			}
			if (!match) {
				throw new RuntimeException("Unexpected character in input: " + s);
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - time));
	}

	@Override
	public Iterator<Token<T>> iterator() {
		return tokens.listIterator();
	}
}

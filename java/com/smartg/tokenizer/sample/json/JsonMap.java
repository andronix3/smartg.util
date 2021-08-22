package com.smartg.tokenizer.sample.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class JsonMap extends LinkedHashMap<String, Object> implements JsonObject {

	private static final long serialVersionUID = 3008196102145313287L;
	private ArrayList<Object> list = new ArrayList<>();

	@Override
	public boolean add(Object obj) {
		list.add(obj);
		while (list.size() > 1) {
			String s = (String) list.remove(0);
			Object o = list.remove(0);
			super.put(s, o);
		}
		return true;
	}

	@Override
	public Object getObject() {
		return this;
	}
}
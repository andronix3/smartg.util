package com.smartg.tokenizer.sample.json;

import java.util.Stack;

public class JsonStack {
	private Stack<JsonObject> stack = new Stack<>();
	private JsonObject jsonObject;

	public void push(String s) {
		jsonObject.add(s);
	}

	public void push(double s) {
		jsonObject.add(s);
	}

	public void push(int s) {
		jsonObject.add(s);
	}

	public void push(JsonObject obj) {
		if (jsonObject != null) {
			jsonObject.add(obj);
		}
		jsonObject = obj;
		stack.push(obj);
	}

	public void closeObject() {
		stack.pop();
		if (!stack.isEmpty()) {
			jsonObject = stack.peek();
		}
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

	public JsonObject getJsonObject() {
		return jsonObject;
	}
}

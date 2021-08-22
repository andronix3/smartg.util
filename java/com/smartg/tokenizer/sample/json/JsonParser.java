package com.smartg.tokenizer.sample.json;

import com.smartg.tokenizer.Token;
import com.smartg.tokenizer.Tokenizer;

public class JsonParser {

	public JsonObject parse(String input) {

		input = input.trim();

		Tokenizer<TokenType> tokenizer = new Tokenizer<>();
		tokenizer.add("\\[", TokenType.LEFT_SQUARE_BRACKET);
		tokenizer.add("\\]", TokenType.RIGHT_SQUARE_BRACKET);
		tokenizer.add("[,]", TokenType.COMMA);
		tokenizer.add("[ ]", TokenType.SPACE);
		tokenizer.add("[:]", TokenType.COLON);
		tokenizer.add("\\{", TokenType.LEFT_CURLY_BRACKET);
		tokenizer.add("\\}", TokenType.RIGHT_CURLY_BRACKET);
		tokenizer.add("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?", TokenType.NUMBER);
		tokenizer.add("\"", TokenType.QUOTATION_MARK);
		tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", TokenType.STRING);

		tokenizer.tokenize(input);

		JsonStack stack = new JsonStack();

		for (Token<TokenType> t : tokenizer) {
			switch (t.token) {
			case LEFT_SQUARE_BRACKET:
				stack.push(new JsonArray());
				break;
			case LEFT_CURLY_BRACKET:
				stack.push(new JsonMap());
				break;
			case NUMBER:
				stack.push(Double.parseDouble(t.sequence));
				break;
			case STRING:
				stack.push(t.sequence);
				break;
			case RIGHT_CURLY_BRACKET:
			case RIGHT_SQUARE_BRACKET:
				stack.closeObject();
				break;
			default:
				break;
			}
		}
		return stack.getJsonObject();
	}

	public static void main(String[] args) {
		final JsonParser jsonParser = new JsonParser();
		System.out.println("First Step");
		JsonObject result = jsonParser.parse(" [ 10, 20, 30.1 ] ");
		if (result != null) {
			System.out.println(result);
		}

		System.out.println("Second Step");
		result = jsonParser.parse(" [ 10 , 20, \"hello\", 30.1 ] ");
		if (result != null) {
			System.out.println(result);
		}

		System.out.println("Third Step");
		result = jsonParser.parse(
				"{" + " \"hello\": \"world\"," + " \"key1\": 20," + " \"key2\": 20.3," + " \"foo\": \"bar\"" + "}");
		if (result != null) {
			System.out.println(result);
		}

		System.out.println("Fourth Step");
		result = jsonParser.parse("{" + " \"hello\" : \"world\"," + " \"key1\" : 20, " + " \"key2\": 20.3, "
				+ " \"foo\": {" + "             \"hello1\": \"world1\"," + "             \"key3\": [200, 300]"
				+ "          }" + "}");
		if (result != null) {
			System.out.println(result);
		}
	}
}

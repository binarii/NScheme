package language;

import java.util.LinkedList;

public class Parser extends LangUtil {

	/**
	 * Tokenize the string into a lexical token list. eg. (sum (- 6 2) 4) =>
	 * [sum, [-, 6, 2], 4]
	 */
	public static Object parse(String s) {
		LinkedList<String> tokens = tokenize(s);
		Object temp = readToken(tokens);
		return temp;
	}

	/**
	 * Splits the initial string into a non lexical tokenized list, this list is
	 * the string split by spaces and will include all characters.
	 */
	public static LinkedList<String> tokenize(String s) {
		String tmp = s.replace("(", " ( ");
		tmp = tmp.replace(")", " ) ");
		String[] tokenArray = tmp.split("[ ]+");
		LinkedList<String> tokens = new LinkedList<String>();
		for (String str : tokenArray) {
			tokens.add(str);
		}
		// First token will but blank
		if (tokens.peek().equals("")) {
			tokens.pop();
		}
		return tokens;
	}

	/**
	 * Read the token list and sort it into a lexical nested list.
	 */
	public static Object readToken(LinkedList<String> tokens) {
		if (tokens.size() == 0) {
			return error("unexpected EOF found");
		}

		String token = tokens.pop();
		if (token.equals("(")) {

			Object obj;
			Object listRef = null;

			while ((obj = tokens.peek()) != null && !obj.equals(")")) {
				listRef = cons(readToken(tokens), listRef);
			}

			if (tokens.size() == 0) {
				return error("unexpected EOF found");
			}

			tokens.pop();
			return reverse(listRef);
		} else if (token.equals(")")) {
			return error("unexpected ')' token");
		} else { // Else it is a variable, number, etc.
			return atomize(token);
		}
	}

	/**
	 * Given a single string, try and parse it to the best format.
	 */
	public static Object atomize(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException er) {
				if (s.equals("#t")) {
					return TRUE;
				} else if (s.equals("#f")) {
					return FALSE;
				} else {
					return s;
				}
			}
		}
	}
}

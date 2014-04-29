package language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import language.exception.EOFException;
import language.exception.LangParseException;
import language.exception.UnexpectedTokenException;
import environment.Table;

public class Parser {

	/**
	 * Tokenize the string into a lexical token list. 
	 * eg. (sum (- 6 2) 4) => [sum, [-, 6, 2], 4]
	 */
	public static Object parse(String s) throws LangParseException {
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
	 * Read the tokenized list and sort it into a lexical nested list.
	 */
	public static Object readToken(LinkedList<String> tokens) throws LangParseException {
		if (tokens.size() == 0) {
			throw new EOFException();
		}

		String token = tokens.pop();
		if (token.equals("(")) {
			LinkedList<Object> l = new LinkedList<Object>();
			Object obj;
			while ((obj = tokens.peek()) != null && !obj.equals(")")) {
				l.add(readToken(tokens));
			}
			if (tokens.size() == 0) {
				throw new EOFException();
			}
			tokens.pop();
			return l;
		} else if (token.equals(")")) {
			throw new UnexpectedTokenException(")");
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
				return Float.parseFloat(s);
			} catch (NumberFormatException er) {
				return s;
			}
		}
	}
}

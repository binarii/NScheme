package language;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class Parser {

	public LinkedList<Object> parse(String s) throws ParseException {
		LinkedList<String> tokens = tokenize(s);
		System.out.println(tokens);
		Object temp = readToken(tokens);
		if (temp instanceof LinkedList) {
			return (LinkedList<Object>) temp;
		}
		return null;
	}

	public LinkedList<String> tokenize(String s) {
		String tmp = s.replace("(", " ( ");
		tmp = tmp.replace(")", " ) ");
		String[] tokenArray = tmp.split("[ ]+");
		LinkedList<String> tokens = new LinkedList<String>();
		for (String str : tokenArray) {
			tokens.add(str);
		}
		tokens.pop();
		return tokens;
	}

	public Object readToken(LinkedList<String> tokens) throws ParseException {
		if (tokens.size() == 0) {
			throw new ParseException("Unexpected EOF found", 0);
		}

		String token = tokens.pop();
		if (token.equals("(")) {
			LinkedList<Object> l = new LinkedList<Object>();
			while (!tokens.peek().equals(")")) {
				l.add(readToken(tokens));
			}
			tokens.pop();
			return l;
		} else if (token.equals(")")) {
			throw new ParseException("Unexpected ')'", 0);
		} else { // Else it is a variable, number, etc.
			return atomize(token);
		}
	}

	public Object atomize(String s) {
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

	public static void main(String[] args) {
		String test = "(add 4 4 (sum A2 A8))";
		Parser parser = new Parser();
		try {
			LinkedList<Object> tokens = parser.parse(test);
			System.out.println(tokens);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

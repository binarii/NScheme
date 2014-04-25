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

	public static Object parse(String s) throws LangParseException {
		LinkedList<String> tokens = tokenize(s);
		Object temp = readToken(tokens);
		return temp;
	}

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
			if(tokens.size() == 0) {
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

	public static void testParser(Parser parser, Environment envr, String test) {
		try {
			Object tokens = Parser.parse(test);
			System.out.println(tokens);

			Object result = Language.eval(tokens, envr);
			System.out.println(result);

		} catch (LangParseException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Parser parser = new Parser();
		Table table = new Table();
		TableEnvironment tableEnvr = new TableEnvironment(table);
		Environment envr = new Environment(tableEnvr);

		table.setEnvironment(envr);
		Language.addDefaultVariables(envr);

		table.getCellAtIndex(0, 0).setString("1");
		table.getCellAtIndex(0, 1).setString("2");
		table.getCellAtIndex(0, 2).setString("3");
		table.getCellAtIndex(0, 3).setString("4");
		table.getCellAtIndex(0, 4).setString("5");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String input = "";
		while (true) {
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			testParser(parser, envr, input);
		}
	}
}

package language;

import java.util.LinkedList;

import environment.Cell;
import environment.Range;
import environment.Table;
import language.exception.ArgumentCountException;
import language.exception.LangParseException;
import language.exception.UndeclaredIdenException;
import language.functions.ArithmaticFunctions;
import language.functions.ConditionalFunctions;
import language.functions.Function;
import language.functions.GeneralFunctions;
import language.functions.StatFunctions;
import language.functions.TrigFunctions;
import language.functions.UserFunction;

public class Language {

	public static Object eval(Object x, Environment env) throws LangParseException {
		if (x instanceof String) {
			// Look up string in environment
			String s = (String) x;
			Object result = env.findVar(s);
			if (result == null) {
				throw new UndeclaredIdenException(s);
			}
			return result;

		} else if (x instanceof LinkedList) {
			// Check built in functions or call procedure
			LinkedList<Object> l = (LinkedList<Object>) x;

			// Evaluate if differently
			if (l.get(0) instanceof String && l.get(0).equals("if")) {
				if (l.size() != 4) {
					throw new ArgumentCountException(3, l.size() - 1);
				}
				Object test = l.get(1);
				Object conseq = l.get(2);
				Object alt = l.get(3);

				Boolean cond = (Boolean) Language.eval(test, env);
				return Language.eval(cond ? conseq : alt, env);

			} else if (l.get(0) instanceof String && l.get(0).equals("lambda")) {
				if (l.size() != 3) {
					throw new ArgumentCountException(2, l.size() - 1);
				}

				Object args = l.get(1);
				Object function = l.get(2);

				return new UserFunction((LinkedList<Object>) args, function, env);
			} else { // Else it is a procedure
				LinkedList<Object> exps = new LinkedList<Object>();
				for (Object t : l) { // Recursively eval all the parameters
					exps.add(Language.eval(t, env));
				}

				if (exps.get(0) instanceof Function) {
					Function proc = (Function) exps.pop();
					return proc.eval(exps);
				}
			}
		} else { // Must be a float or integer
			return x;
		}

		throw new LangParseException("ERROR: Error parsing input");
	}

	public static LinkedList<Cell> getFormulaReferences(String s, Environment envr) {
		LinkedList<Cell> cells = new LinkedList<Cell>();
		LinkedList<String> tokens = Parser.tokenize(s);
		Table table = envr.getTable();
		TableEnvironment tEvnt = new TableEnvironment(table);

		for (String t : tokens) {
			Cell ref = tEvnt.parseCell(t);
			if (ref != null) {
				cells.add(ref);
			}

			Range rangeRef = tEvnt.parseRange(t);
			if (rangeRef != null) {
				for (Cell c : rangeRef) {
					cells.add(c);
				}
			}
		}

		return cells;
	}

	public static void addDefaultVariables(Environment envr) {

		envr.putVar("#f", false);
		envr.putVar("#t", true);

		envr.putVar("PI", Math.PI);
		envr.putVar("EULER", Math.E);

		ArithmaticFunctions.addFunctions(envr);
		ConditionalFunctions.addFunctions(envr);
		GeneralFunctions.addFunctions(envr);
		StatFunctions.addFunctions(envr);
		TrigFunctions.addFunctions(envr);
	}
}

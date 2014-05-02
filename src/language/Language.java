package language;

import java.util.LinkedList;

import environment.Cell;
import environment.Range;
import environment.Table;
import language.exception.ArgumentCountException;
import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.exception.UndeclaredIdenException;
import language.functions.ArithmaticFunctions;
import language.functions.ConditionalFunctions;
import language.functions.Function;
import language.functions.GeneralFunctions;
import language.functions.LangMath;
import language.functions.StatFunctions;
import language.functions.TrigFunctions;
import language.functions.UserFunction;

public class Language {

	/**
	 * The evaluate function recursively evaluates the arguments of the
	 * function.
	 */
	public static Object eval(Object x, Environment env) throws LangParseException {
		// If the object is a string, find its reference in the environment.
		if (x instanceof String) {
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
				LangMath.validateParamCount(l, 4);

				Object test = l.get(1);
				Object conseq = l.get(2);
				Object alt = l.get(3);

				Boolean cond = (Boolean) Language.eval(test, env);
				return Language.eval(cond ? conseq : alt, env);

			} else if (l.get(0) instanceof String && l.get(0).equals("lambda")) {
				LangMath.validateParamCount(l, 3);

				Object args = l.get(1);
				Object function = l.get(2);

				return new UserFunction((LinkedList<Object>) args, function, env);
			} else if (l.get(0) instanceof String && l.get(0).equals("set!")) {
				LangMath.validateParamCount(l, 3);

				Object iden = l.get(1);
				Object value = Language.eval(l.get(2), env);

				if (iden instanceof String) {
					env.setVar((String) iden, value);
				} else {
					throw new ArgumentTypeException(String.class, iden.getClass(), 0);
				}

				return value;
			} else if (l.get(0) instanceof String && l.get(0).equals("define")) {
				LangMath.validateParamCount(l, 3);

				Object iden = l.get(1);
				Object value = Language.eval(l.get(2), env);

				if (iden instanceof String) {
					env.putVar((String) iden, value);
				} else {
					throw new ArgumentTypeException(String.class, iden.getClass(), 0);
				}

				return value;
			} else {
				/*
				 * Otherwise the input is a linked list. In this case
				 * recursively evaluate all parameters and attempt to call the
				 * first argument (a function) on the rest of the parameters.
				 */
				LinkedList<Object> exps = new LinkedList<Object>();

				// Recursively evaluate all the parameters
				for (Object t : l) {
					exps.add(Language.eval(t, env));
				}

				// Call the function specified
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

	/**
	 * Return a linked list of cells that the string references in its function.
	 */
	public static LinkedList<Cell> getFormulaReferences(String s, Environment envr) {
		LinkedList<Cell> cells = new LinkedList<Cell>();
		LinkedList<String> tokens = Parser.tokenize(s);
		Table table = envr.getTable();
		TableEnvironment tEvnt = new TableEnvironment(table);

		for (String t : tokens) {
			// Check if it is a cell
			Cell ref = tEvnt.parseCell(t);
			if (ref != null) {
				cells.add(ref);
			}

			// Check if it is a range, add all the cells
			Range rangeRef = tEvnt.parseRange(t);
			if (rangeRef != null) {
				for (Cell c : rangeRef) {
					cells.add(c);
				}
			}
		}

		return cells;
	}

	/**
	 * Add the default variables to the specified environment.
	 */
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

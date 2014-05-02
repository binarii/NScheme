package language;

import java.util.LinkedList;

import environment.Cell;
import environment.Range;
import environment.Table;
import language.functions.ConditionalFunctions;
import language.functions.CoreFunctions;
import language.functions.Function;
import language.functions.GeneralFunctions;
import language.functions.StatFunctions;
import language.functions.TrigFunctions;
import language.functions.Closure;

public class Language extends LangUtil {

	/**
	 * The evaluate function recursively evaluates the arguments of the
	 * function.
	 */
	public static Object eval(Object x, Environment env) {
		// If the object is a string, find its reference in the environment.
		if (x instanceof String) {
			String s = (String) x;
			Object result = env.findVar(s);
			return result;

		} else if (!(x instanceof Pair)) {
			// Must be an integer or double
			return x;
		} else {
			Object fn = first(x);
			Object args = rest(x);

			// Evaluate if differently
			if (fn.equals("if")) {
				validateArgCount(args, 3);

				Object test = first(args);
				Object conseq = second(args);
				Object alt = third(args);

				Boolean cond = (Boolean) Language.eval(test, env);
				return Language.eval(cond ? conseq : alt, env);

			} else if (fn.equals("lambda")) {
				validateArgCount(args, 2);

				Object arguments = first(args);
				Object function = second(args);

				return new Closure(arguments, function, env);
			} else if (fn.equals("set!")) {
				validateArgCount(args, 2);

				Object iden = first(args);
				Object value = Language.eval(second(args), env);
				env.putVar(str(iden), value);

				return value;
			} else if (fn.equals("define")) {
				validateArgCount(args, 2);

				Object iden = first(args);
				Object value = Language.eval(second(args), env);
				env.putVar(str(iden), value);

				return value;
			} else {
				/*
				 * Otherwise the input is a linked list. In this case
				 * recursively evaluate all parameters and attempt to call the
				 * first argument (a function) on the rest of the parameters.
				 */
				fn = eval(fn, env);
				args = evalList(args, env);

				// Call the function specified
				if (fn instanceof Function) {
					Function proc = (Function) fn;
					return proc.apply(args);
				}
			}
		}

		return error("error parsing input");
	}

	protected static Pair evalList(Object list, Environment env) {
		if (list == null) {
			return null;
		} else if (!(list instanceof Pair)) {
			error("Illegal argument list");
			return null;
		} else {
			return cons(eval(first(list), env), evalList(rest(list), env));
		}
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

		CoreFunctions.addFunctions(envr);
		ConditionalFunctions.addFunctions(envr);
		GeneralFunctions.addFunctions(envr);
		StatFunctions.addFunctions(envr);
		TrigFunctions.addFunctions(envr);
	}
}

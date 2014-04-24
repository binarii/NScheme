package language;

import java.text.ParseException;
import java.util.LinkedList;

import language.Functions.Function;
import language.Functions.Arithmatic.FuncAdd;
import language.Functions.Arithmatic.FuncDivide;
import language.Functions.Arithmatic.FuncMultiply;
import language.Functions.Arithmatic.FuncSubtract;
import language.Functions.conditional.FuncEquals;
import language.Functions.conditional.FuncGreaterThan;
import language.Functions.conditional.FuncLessThan;
import language.Functions.stat.FuncAverage;
import language.Functions.stat.FuncMax;
import language.Functions.stat.FuncMin;
import language.Functions.stat.FuncSum;
import environment.Environment;

public class Language {

	public static Object eval(Object x, Environment env) throws ParseException {
		if (x instanceof String) {
			// Look up string in environment
			String s = (String) x;
			Object result = env.findVar(s);
			if(result == null) {
				throw new ParseException("Identifier not found: " + s, 0);
			}
			return result;

		} else if (x instanceof LinkedList) {
			// Check built in functions or call procedure
			LinkedList<Object> l = (LinkedList<Object>) x;

			// Fist element should always be a string
			String s = (String) l.get(0);

			// Evaluate if differently
			if (s.equals("if")) {
				if (l.size() != 4) {
					throw new ParseException("Incorrect parameters", 0);
				}
				Object test = l.get(1);
				Object conseq = l.get(2);
				Object alt = l.get(3);

				Boolean cond = (Boolean) Language.eval(test, env);
				return Language.eval(cond ? conseq : alt, env);

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

		throw new ParseException("Error parsing input", 0);
	}
	
	public static void addDefaultVariables(Environment envr) {

		envr.putVar("PI", new Float(3.14159));
		envr.putVar("#t", new Boolean(true));
		envr.putVar("#f", new Boolean(false));

		envr.putVar("+", new FuncAdd());
		envr.putVar("-", new FuncSubtract());
		envr.putVar("/", new FuncDivide());
		envr.putVar("*", new FuncMultiply());
		
		envr.putVar("sum", new FuncSum());
		envr.putVar("average", new FuncAverage());
		envr.putVar("max", new FuncMax());
		envr.putVar("min", new FuncMin());
		
		envr.putVar("=", new FuncEquals());
		envr.putVar("<", new FuncLessThan());
		envr.putVar(">", new FuncGreaterThan());
	}
}

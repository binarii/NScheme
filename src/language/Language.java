package language;

import java.util.LinkedList;

import language.exception.ArgumentCountException;
import language.exception.LangParseException;
import language.exception.UndeclaredIdenException;
import language.functions.ArithmaticFunctions;
import language.functions.ConditionalFunctions;
import language.functions.Function;
import language.functions.GeneralFunctions;
import language.functions.StatFunctions;
import language.functions.TrigFunctions;

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

			// Fist element should always be a string
			String s = (String) l.get(0);

			// Evaluate if differently
			if (s.equals("if")) {
				if (l.size() != 4) {
					throw new ArgumentCountException(3, l.size() - 1);
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

		throw new LangParseException("ERROR: Error parsing input");
	}

	public static void addDefaultVariables(Environment envr) {

		ArithmaticFunctions.addFunctions(envr);
		ConditionalFunctions.addFunctions(envr);
		GeneralFunctions.addFunctions(envr);
		StatFunctions.addFunctions(envr);
		TrigFunctions.addFunctions(envr);
	}
}

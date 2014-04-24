package language;

import java.util.LinkedList;

import language.exception.ArgumentCountException;
import language.exception.LangParseException;
import language.exception.UndeclaredIdenException;
import language.functions.Function;
import language.functions.arithmatic.FuncAdd;
import language.functions.arithmatic.FuncDivide;
import language.functions.arithmatic.FuncMultiply;
import language.functions.arithmatic.FuncSubtract;
import language.functions.conditional.FuncEquals;
import language.functions.conditional.FuncGreaterThan;
import language.functions.conditional.FuncLessThan;
import language.functions.math.FuncAbs;
import language.functions.math.FuncCeiling;
import language.functions.math.FuncFloor;
import language.functions.math.FuncModulus;
import language.functions.math.FuncPower;
import language.functions.math.FuncRandom;
import language.functions.math.FuncSqrt;
import language.functions.stat.FuncAverage;
import language.functions.stat.FuncMax;
import language.functions.stat.FuncMin;
import language.functions.stat.FuncSum;
import language.functions.trig.FuncCos;
import language.functions.trig.FuncSin;
import language.functions.trig.FuncTan;

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

		envr.putVar("PI", new Float(Math.PI));
		envr.putVar("#t", new Boolean(true));
		envr.putVar("#f", new Boolean(false));

		envr.putVar("+", new FuncAdd());
		envr.putVar("-", new FuncSubtract());
		envr.putVar("/", new FuncDivide());
		envr.putVar("*", new FuncMultiply());

		envr.putVar("sum", new FuncSum());
		envr.putVar("avg", new FuncAverage());
		envr.putVar("max", new FuncMax());
		envr.putVar("min", new FuncMin());

		envr.putVar("sin", new FuncSin());
		envr.putVar("cos", new FuncCos());
		envr.putVar("tan", new FuncTan());

		envr.putVar("abs", new FuncAbs());
		envr.putVar("mod", new FuncModulus());
		envr.putVar("pow", new FuncPower());
		envr.putVar("rand", new FuncRandom());
		envr.putVar("sqrt", new FuncSqrt());
		envr.putVar("ceil", new FuncCeiling());
		envr.putVar("floor", new FuncFloor());

		envr.putVar("=", new FuncEquals());
		envr.putVar("<", new FuncLessThan());
		envr.putVar(">", new FuncGreaterThan());
	}
}

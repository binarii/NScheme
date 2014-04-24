package language.functions.math;

import java.util.LinkedList;

import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.functions.BasicFunction;

public class FuncAbs extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		validateArgCount(args, 1);
		if (args.get(0) instanceof Integer) {
			return Math.abs((Integer) args.get(0));
		} else if (args.get(0) instanceof Float) {
			return Math.abs((Float) args.get(0));
		} else {
			throw new ArgumentTypeException(Number.class, args.get(0).getClass(), 0);
		}
	}
}

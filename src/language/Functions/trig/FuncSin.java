package language.functions.trig;

import java.util.LinkedList;

import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.functions.BasicFunction;

public class FuncSin extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		validateArgCount(args, 1);
		if (args.get(0) instanceof Number) {
			Number a1 = (Number) args.get(0);
			return Math.sin(a1.floatValue());
		} else {
			throw new ArgumentTypeException(Number.class, args.get(0).getClass(), 0);
		}
	}
}

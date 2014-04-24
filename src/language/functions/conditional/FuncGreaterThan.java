package language.functions.conditional;

import java.util.LinkedList;

import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.functions.BasicFunction;

public class FuncGreaterThan extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		validateArgCount(args, 2);
		if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
			Number a1 = (Number) args.get(0);
			Number a2 = (Number) args.get(1);
			return a1.floatValue() > a2.floatValue();
		} else {
			throw new ArgumentTypeException(Number.class, args.get(1).getClass(), 0);
		}
	}
}

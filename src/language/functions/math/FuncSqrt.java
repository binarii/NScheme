package language.functions.math;

import java.text.ParseException;
import java.util.LinkedList;

import language.functions.BasicFunction;

public class FuncSqrt extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 1);
		if (args.get(0) instanceof Number) {
			Number a1 = (Number) args.get(0);
			return Math.sqrt(a1.floatValue());
		} else {
			throw new ParseException("Incorrect parameters", 0);
		}
	}
}

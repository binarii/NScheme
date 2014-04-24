package language.Functions.math;

import java.text.ParseException;
import java.util.LinkedList;

import language.Functions.BasicFunction;

public class FuncCeiling extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 1);
		if (args.get(0) instanceof Number) {
			Number a1 = (Number) args.get(0);
			return Math.ceil(a1.floatValue());
		} else {
			throw new ParseException("Incorrect parameters", 0);
		}
	}
}

package language.functions.trig;

import java.text.ParseException;
import java.util.LinkedList;

import language.functions.BasicFunction;

public class FuncCos extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 1);
		if (args.get(0) instanceof Number) {
			Number a1 = (Number) args.get(0);
			return Math.cos(a1.floatValue());
		} else {
			throw new ParseException("Incorrect parameters", 0);
		}
	}
}

package language.Functions.trig;

import java.text.ParseException;
import java.util.LinkedList;

import language.Functions.BasicFunction;

public class FuncTan extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 1);
		if (args.get(1) instanceof Number) {
			Number a1 = (Number) args.get(1);
			return Math.tan(a1.floatValue());
		} else {
			throw new ParseException("Incorrect parameters", 0);
		}
	}
}

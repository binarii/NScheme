package language.Functions.conditional;

import java.text.ParseException;
import java.util.LinkedList;

import language.Functions.BasicFunction;

public class FuncLessThan extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 2);
		if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
			Number a1 = (Number) args.get(0);
			Number a2 = (Number) args.get(1);
			return a1.floatValue() < a2.floatValue();
		} else {
			throw new ParseException("Incorrect parameters", 0);
		}
	}
}

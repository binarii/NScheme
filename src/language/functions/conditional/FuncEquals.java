package language.functions.conditional;

import java.text.ParseException;
import java.util.LinkedList;

import language.functions.BasicFunction;

public class FuncEquals extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 2);

		return args.get(0).equals(args.get(1));
	}
}

package language.functions.conditional;

import java.util.LinkedList;

import language.exception.LangParseException;
import language.functions.BasicFunction;

public class FuncEquals extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		validateArgCount(args, 2);

		return args.get(0).equals(args.get(1));
	}
}

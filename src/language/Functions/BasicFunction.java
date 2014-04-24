package language.Functions;

import java.text.ParseException;
import java.util.LinkedList;

public abstract class BasicFunction implements Function {

	@Override
	public abstract Object eval(LinkedList<Object> args) throws ParseException;

	protected void validateArgCount(LinkedList<Object> args, int count) throws ParseException {
		if (args.size() != count) {
			throw new ParseException("Incorrect number of arguments", 0);
		}
	}
}

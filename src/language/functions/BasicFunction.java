package language.functions;

import java.util.LinkedList;

import language.exception.ArgumentCountException;
import language.exception.LangParseException;

public abstract class BasicFunction implements Function {

	@Override
	public abstract Object eval(LinkedList<Object> args) throws LangParseException;

	protected void validateArgCount(LinkedList<Object> args, int count) throws LangParseException {
		if (args.size() != count) {
			throw new ArgumentCountException(count, args.size());
		}
	}
}

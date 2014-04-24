package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.exception.LangParseException;

public class ConditionalFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar(">", new GreaterThanFunction());
		envr.putVar("<", new LessThanFunction());
		envr.putVar("=", new EqualsFunction());
	}

	private static class GreaterThanFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 2);
			LangMath.validateNumber(args.get(0));
			LangMath.validateNumber(args.get(1));

			return LangMath.greaterThan((Number) args.get(0), (Number) args.get(1));
		}
	}

	private static class LessThanFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 2);
			LangMath.validateNumber(args.get(0));
			LangMath.validateNumber(args.get(1));

			return LangMath.lessThan((Number) args.get(0), (Number) args.get(1));
		}
	}

	private static class EqualsFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 2);

			return args.get(0).equals(args.get(1));
		}
	}
}

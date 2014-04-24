package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.exception.LangParseException;

public class TrigFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar("sin", new SinFunction());
		envr.putVar("cos", new CosFunction());
		envr.putVar("tan", new TanFunction());
	}

	private static class SinFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.sin((Number) args.get(0));
		}
	}

	private static class CosFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.cos((Number) args.get(0));
		}
	}

	private static class TanFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.tan((Number) args.get(0));
		}
	}
}

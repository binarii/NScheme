package language.functions;

import java.util.LinkedList;
import java.util.Random;

import language.Environment;
import language.exception.LangParseException;

public class GeneralFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar("abs", new AbsFunction());
		envr.putVar("ceil", new CeilFunction());
		envr.putVar("floor", new FloorFunction());
		envr.putVar("sqrt", new SqrtFunction());
		envr.putVar("mod", new ModFunction());
		envr.putVar("pow", new PowerFunction());
		envr.putVar("rand", new RandFunction());
		envr.putVar("int", new IntFunction());
	}

	private static class AbsFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.abs((Number) args.get(0));
		}
	}

	private static class CeilFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.ceiling((Number) args.get(0));
		}
	}

	private static class FloorFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.floor((Number) args.get(0));
		}
	}

	private static class SqrtFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return LangMath.sqrt((Number) args.get(0));
		}
	}

	private static class ModFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 2);
			LangMath.validateNumber(args.get(0));
			LangMath.validateNumber(args.get(1));

			return LangMath.mod((Number) args.get(0), (Number) args.get(1));
		}
	}

	private static class PowerFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 2);
			LangMath.validateNumber(args.get(0));
			LangMath.validateNumber(args.get(1));

			return LangMath.power((Number) args.get(0), (Number) args.get(1));
		}
	}

	private static class RandFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 0);

			return new Random().nextInt();
		}
	}

	private static class IntFunction implements Function {
		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);
			LangMath.validateNumber(args.get(0));

			return ((Number) args.get(0)).intValue();
		}
	}
}

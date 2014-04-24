package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.exception.ArgumentCountException;
import language.exception.ArgumentTypeException;
import language.exception.LangParseException;

public class ConditionalFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar(">", new GreaterThanFunction());
		envr.putVar("<", new LessThanFunction());
		envr.putVar("=", new EqualsFunction());
		envr.putVar("not", new NotFunction());
		envr.putVar("or", new OrFunction());
		envr.putVar("and", new AndFunction());
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

	private static class NotFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			LangMath.validateParamCount(args, 1);

			if (args.get(0) instanceof Boolean) {
				return !((Boolean) args.get(0));
			} else {
				throw new ArgumentTypeException(Boolean.class, args.get(0).getClass(), 0);
			}
		}
	}

	private static class AndFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			if (args.size() == 0) {
				throw new ArgumentCountException(2, 0);
			}

			for (Object i : args) {
				if (i instanceof Boolean) {
					if (!(Boolean) i) {
						return false;
					}
				} else {
					throw new ArgumentTypeException(Boolean.class, i.getClass(), 0);
				}
			}
			return true;
		}
	}

	private static class OrFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			if (args.size() == 0) {
				throw new ArgumentCountException(2, 0);
			}

			for (Object i : args) {
				if (i instanceof Boolean) {
					if ((Boolean) i) {
						return true;
					}
				} else {
					throw new ArgumentTypeException(Boolean.class, i.getClass(), 0);
				}
			}
			return false;
		}
	}
}

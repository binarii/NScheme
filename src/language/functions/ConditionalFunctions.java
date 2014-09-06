package language.functions;

import language.Environment;
import language.LangUtil;
import language.Pair;

public class ConditionalFunctions extends LangUtil {

	public static void addFunctions(Environment envr) {
		envr.putVar(">", new CompFunction(GT));
		envr.putVar(">=", new CompFunction(GTE));
		envr.putVar("<", new CompFunction(LT));
		envr.putVar("<=", new CompFunction(LTE));
		envr.putVar("=", new CompFunction(EQUALS));
		envr.putVar("or", new BooleanFunction(OR));
		envr.putVar("and", new BooleanFunction(AND));
		envr.putVar("not", new NotFunction());
	}

	private static final int GT = 0;
	private static final int GTE = 1;
	private static final int LT = 2;
	private static final int LTE = 3;
	private static final int EQUALS = 4;
	private static final int OR = 5;
	private static final int AND = 6;

	public static Object applyCompare(Object args, int op) {
		while (rest(args) instanceof Pair) {
			double x = numDouble(first(args));
			args = rest(args);
			double y = numDouble(first(args));

			switch (op) {
			case GT:
				if (x <= y)
					return FALSE;
				break;
			case GTE:
				if (x < y)
					return FALSE;
				break;
			case LT:
				if (x >= y)
					return FALSE;
				break;
			case LTE:
				if (x > y)
					return FALSE;
				break;
			case EQUALS:
				if (x != y)
					return FALSE;
				break;
			}
		}

		return TRUE;
	}

	public static Object applyBoolean(Object args, int op) {
		while (args instanceof Pair) {
			boolean curr = bool(first(args));
			args = rest(args);

			switch (op) {
			case OR:
				if (curr)
					return TRUE;
				break;
			case AND:
				if (!curr)
					return FALSE;
				break;
			}
		}

		if (op == OR) {
			return FALSE;
		} else {
			return TRUE;
		}
	}

	private static class BooleanFunction extends Function {
		private int _type;

		public BooleanFunction(int type) {
			_type = type;
		}

		@Override
		public Object apply(Object args) {
			switch (_type) {
			case OR:
				return applyBoolean(args, OR);
			case AND:
				return applyBoolean(args, AND);
			default:
				return null;
			}
		}
	}

	private static class CompFunction extends Function {
		private int _type;

		public CompFunction(int type) {
			_type = type;
		}

		@Override
		public Object apply(Object args) {
			if (_type >= GT && _type <= EQUALS) {
				return applyCompare(args, _type);
			} else {
				return null;
			}
		}
	}

	private static class NotFunction extends Function {
		@Override
		public Object apply(Object args) {
			validateArgCount(args, 1);
			boolean b = bool(first(args));
			return !b;
		}
	}
}

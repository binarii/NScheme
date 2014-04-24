package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.exception.LangParseException;

public class ArithmaticFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar("+", new OpApplyFunction(new SimpleOpAdd()));
		envr.putVar("-", new OpApplyFunction(new SimpleOpSubtract()));
		envr.putVar("/", new OpApplyFunction(new SimpleOpDivide()));
		envr.putVar("*", new OpApplyFunction(new SimpleOpMultiply()));
	}

	private static class OpApplyFunction implements Function {
		private ApplyOp _op;

		public OpApplyFunction(ApplyOp op) {
			_op = op;
		}

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			return LangMath.simpleApplyNoRange(_op, args);
		}
	}

	private static class SimpleOpAdd implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.add(a1, a2);
		}
	}

	private static class SimpleOpSubtract implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.subtract(a1, a2);
		}
	}

	private static class SimpleOpDivide implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.divide(a1, a2);
		}
	}

	private static class SimpleOpMultiply implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.multiply(a1, a2);
		}
	}
}

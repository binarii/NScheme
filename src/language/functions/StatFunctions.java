package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.exception.LangParseException;

public class StatFunctions {

	public static void addFunctions(Environment envr) {
		envr.putVar("sum", new OpApplyFunction(new SimpleOpAdd()));
		envr.putVar("max", new OpApplyFunction(new SimpleOpMax()));
		envr.putVar("min", new OpApplyFunction(new SimpleOpMin()));
		envr.putVar("avg", new AverageFunction());
	}

	private static class AverageFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			Number size = LangMath.length(args);
			Number total = (Number) LangMath.simpleApply(new SimpleOpAdd(), args);
			return total.floatValue() / size.floatValue();
		}
	}

	private static class OpApplyFunction implements Function {
		private ApplyOp _op;

		public OpApplyFunction(ApplyOp op) {
			_op = op;
		}

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			return LangMath.simpleApply(_op, args);
		}
	}

	private static class SimpleOpAdd implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.add(a1, a2);
		}
	}

	private static class SimpleOpMax implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.max(a1, a2);
		}
	}

	private static class SimpleOpMin implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			return LangMath.min(a1, a2);
		}
	}
}

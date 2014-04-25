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
		envr.putVar("mean", new AverageFunction());
		envr.putVar("variance", new VarianceFunction());
		envr.putVar("stddev", new VarianceFunction());
	}

	private static class AverageFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			Number size = LangMath.length(args);
			Number total = (Number) LangMath.simpleApply(null, new SimpleOpAdd(), args);
			return total.floatValue() / size.floatValue();
		}
	}

	private static class VarianceFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			Number size = LangMath.length(args);
			Number total = (Number) LangMath.simpleApply(null, new SimpleOpAdd(), args);
			Number mean = total.floatValue() / size.floatValue();

			Number varSum = (Number) LangMath.simpleApply(null, new VarianceAddOp(mean), args);
			return varSum.floatValue() / size.floatValue();
		}
	}

	private static class StddevFunction implements Function {

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			Number size = LangMath.length(args);
			Number total = (Number) LangMath.simpleApply(null, new SimpleOpAdd(), args);
			Number mean = total.floatValue() / size.floatValue();

			Number varSum = (Number) LangMath.simpleApply(null, new VarianceAddOp(mean), args);
			return Math.sqrt(varSum.floatValue() / size.floatValue());
		}
	}

	private static class OpApplyFunction implements Function {
		private ApplyOp _op;

		public OpApplyFunction(ApplyOp op) {
			_op = op;
		}

		@Override
		public Object eval(LinkedList<Object> args) throws LangParseException {
			return LangMath.simpleApply(null, _op, args);
		}
	}

	private static class SimpleOpAdd implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			if (a1 == null)
				return a2;
			return LangMath.add(a1, a2);
		}
	}

	private static class VarianceAddOp implements ApplyOp {
		private Number _mean;

		public VarianceAddOp(Number mean) {
			_mean = mean;
		}

		@Override
		public Object eval(Number a1, Number a2) {
			if (a1 == null)
				a1 = 0;
			float term = (a2.floatValue() - _mean.floatValue());
			term = term * term;

			return a1.floatValue() + term;
		}
	}

	private static class SimpleOpMax implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			if (a1 == null)
				return a2;
			return LangMath.max(a1, a2);
		}
	}

	private static class SimpleOpMin implements ApplyOp {
		@Override
		public Object eval(Number a1, Number a2) {
			if (a1 == null)
				return a2;
			return LangMath.min(a1, a2);
		}
	}
}

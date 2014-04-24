package language.functions.arithmatic;

import java.util.LinkedList;

import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.functions.Function;

public abstract class ArithmaticFunction implements Function {

	@Override
	public abstract Object eval(LinkedList<Object> args) throws LangParseException;

	protected Object simpleApply(SimpleOp op, LinkedList<Object> args) throws LangParseException {
		float result = getFloat(args.pop());
		boolean floatParams = false;

		for (Object i : args) {
			if (i instanceof Float) {
				floatParams = true;
			}

			if (i instanceof Number) {
				result = op.eval(result, getFloat(i));
			} else {
				throw new ArgumentTypeException(Number.class, i.getClass(), 0);
			}
		}

		if (floatParams) {
			return result;
		} else {
			return new Integer((int) result);
		}
	}

	private float getFloat(Object o) {
		if (o instanceof Number) {
			return ((Number) o).floatValue();
		} else {
			return 0;
		}
	}

	protected interface SimpleOp {
		public float eval(float a1, float a2);
	}
}

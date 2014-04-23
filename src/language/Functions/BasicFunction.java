package language.Functions;

import java.text.ParseException;
import java.util.LinkedList;

public abstract class BasicFunction implements Function {

	@Override
	public abstract Object eval(LinkedList<Object> args) throws ParseException;

	protected boolean checkParameterCount(LinkedList<Object> args, int count) {
		return (args.size() - 1) == count;
	}

	protected Object simpleApply(SimpleOp op, LinkedList<Object> args) throws ParseException {
		float result = getFloat(args.pop());
		boolean floatParams = false;

		for (Object i : args) {
			if (i instanceof Float) {
				floatParams = true;
			}

			if (i instanceof Number) {
				result = op.eval(result, getFloat(i));
			} else {
				throw new ParseException("Incorrect parameters", 0);
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
		} else
			return 0;
	}

	protected interface SimpleOp {
		public float eval(float a1, float a2);
	}
}

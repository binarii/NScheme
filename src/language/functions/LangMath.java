package language.functions;

import java.util.LinkedList;
import java.util.List;

import environment.Cell;
import environment.Range;
import language.Parser;
import language.exception.ArgumentCountException;
import language.exception.ArgumentTypeException;
import language.exception.LangParseException;

public class LangMath {

	public static Object simpleApply(ApplyOp op, LinkedList<Object> list)
			throws LangParseException {
		Object accumulator = list.peek();
		boolean first = true;

		// If the first element is a range
		if (accumulator instanceof Range) {
			accumulator = simpleApply(op, rangeToList((Range) accumulator));
		}

		for (Object i : list) {
			if (first) { // Dont use the first element
				first = false;
				continue;
			}

			if (i instanceof Number) {
				accumulator = op.eval((Number) accumulator, (Number) i);
			} else if (i instanceof Range) {
				Object temp = simpleApply(op, rangeToList((Range) i));
				accumulator = op.eval((Number) accumulator, (Number) temp);
			} else {
				throw new ArgumentTypeException(Number.class, i.getClass(), 0);
			}
		}

		return accumulator;
	}

	public static Object simpleApplyNoRange(ApplyOp op, LinkedList<Object> list)
			throws LangParseException {
		Object accumulator = list.peek();
		validateNumber(accumulator);
		boolean first = true;

		for (Object i : list) {
			if (first) { // Dont use the first element
				first = false;
				continue;
			}

			if (i instanceof Number) {
				accumulator = op.eval((Number) accumulator, (Number) i);
			} else {
				throw new ArgumentTypeException(Number.class, i.getClass(), 0);
			}
		}

		return accumulator;
	}

	// FIXED VARIABLE COUNT FUNCTIONS
	public static Object sin(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.sin(((Number) n1).floatValue());
	}

	public static Object cos(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.cos(((Number) n1).floatValue());
	}

	public static Object tan(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.tan(((Number) n1).floatValue());
	}

	public static Object ceiling(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.ceil(((Number) n1).floatValue());
	}

	public static Object floor(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.floor(((Number) n1).floatValue());
	}

	public static Object sqrt(Object n1) throws LangParseException {
		validateNumber(n1);
		return Math.sqrt(((Number) n1).floatValue());
	}

	public static Object abs(Object n1) throws LangParseException {
		validateNumber(n1);
		if (n1 instanceof Integer) {
			return Math.abs(((Number) n1).intValue());
		} else {
			return Math.abs(((Number) n1).floatValue());
		}
	}

	public static Object mod(Object n1, Object n2) throws LangParseException {
		validateNumber(n1);
		validateNumber(n2);

		if (n1 instanceof Integer && n2 instanceof Integer) {
			return ((Number) n1).intValue() % ((Number) n2).intValue();
		} else {
			return ((Number) n1).floatValue() % ((Number) n2).floatValue();
		}
	}

	public static Object power(Object n1, Object n2) throws LangParseException {
		validateNumber(n1);
		validateNumber(n2);

		return Math.pow(((Number) n1).floatValue(), ((Number) n2).floatValue());
	}

	// GENERAL MATH FUNCTIONS
	public static Number add(Number n1, Number n2) {
		float result = n1.floatValue() + n2.floatValue();

		if ((n1 instanceof Float) || (n2 instanceof Float)) {
			return new Float(result);
		}
		return new Integer((int) result);
	}

	public static Number subtract(Number n1, Number n2) {
		float result = n1.floatValue() - n2.floatValue();

		if ((n1 instanceof Float) || (n2 instanceof Float)) {
			return new Float(result);
		}
		return new Integer((int) result);
	}

	public static Number multiply(Number n1, Number n2) {
		float result = n1.floatValue() * n2.floatValue();

		if ((n1 instanceof Float) || (n2 instanceof Float)) {
			return new Float(result);
		}
		return new Integer((int) result);
	}

	public static Number divide(Number n1, Number n2) {
		float result = n1.floatValue() / n2.floatValue();

		if ((n1 instanceof Float) || (n2 instanceof Float)) {
			return new Float(result);
		}
		return new Integer((int) result);
	}

	public static Boolean lessThan(Number n1, Number n2) {
		return n1.floatValue() < n2.floatValue();
	}

	public static Object max(Number n1, Number n2) {
		if (n1.floatValue() > n2.floatValue()) {
			return n1;
		}
		return n2;
	}

	public static Object min(Number n1, Number n2) {
		if (n1.floatValue() < n2.floatValue()) {
			return n1;
		}
		return n2;
	}

	public static Boolean greaterThan(Number n1, Number n2) {
		return n1.floatValue() > n2.floatValue();
	}

	// HELPER FUNCTIONS
	public static int length(LinkedList<Object> list) {
		int count = 0;
		for (Object i : list) {
			if (i instanceof Range) {
				count += ((Range) i).size();
			} else {
				count++;
			}
		}
		return count;
	}

	public static void validateParamCount(LinkedList<Object> list, int count)
			throws LangParseException {
		if (list.size() != count) {
			throw new ArgumentCountException(count, list.size());
		}
	}

	public static void validateNumber(Object n1) throws LangParseException {
		if (!(n1 instanceof Number)) {
			throw new ArgumentTypeException(Number.class, n1.getClass(), 0);
		}
	}

	public static void validateNumberList(List<Object> list) throws LangParseException {
		for (Object i : list) {
			validateNumber(i);
		}
	}

	public static LinkedList<Object> rangeToList(Range range) {
		LinkedList<Object> result = new LinkedList<Object>();

		for (Cell i : range) {
			result.add(Parser.atomize(i.getString()));
		}
		return result;
	}
}

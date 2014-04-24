package language.functions.stat;

import java.util.LinkedList;

import environment.Cell;
import environment.Range;
import language.Parser;
import language.exception.ArgumentTypeException;
import language.exception.LangParseException;
import language.functions.Function;

public class FuncAverage implements Function {
	private int count;

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		float result = 0.0f;
		count = 0;
		boolean floatParams = false;

		for (Object i : args) {
			if (i instanceof Range) {
				i = eval((Range) i);
				count--; // Discount the following addition
			}

			if (i instanceof Float) {
				floatParams = true;
			}

			if (i instanceof Number) {
				result = result + ((Number) i).floatValue();
				count++;
			} else {
				throw new ArgumentTypeException(Number.class, i.getClass(), 0);
			}
		}

		result = result / (float) count;
		if (floatParams) {
			return result;
		} else {
			return new Integer((int) result);
		}
	}

	public Object eval(Range range) throws LangParseException {
		float result = 0.0f;
		boolean floatParams = false;

		for (Cell i : range) {
			Object val = Parser.atomize(i.getString());

			if (val instanceof Float) {
				floatParams = true;
			}

			if (val instanceof Number) {
				result = result + ((Number) val).floatValue();
				count++;
			} else {
				throw new ArgumentTypeException(Number.class, val.getClass(), 0);
			}
		}

		if (floatParams) {
			return result;
		} else {
			return new Integer((int) result);
		}

	}
}

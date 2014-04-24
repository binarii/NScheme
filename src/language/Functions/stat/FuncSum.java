package language.Functions.stat;

import java.text.ParseException;
import java.util.LinkedList;

import environment.Cell;
import environment.Range;
import language.Parser;
import language.Functions.Function;

public class FuncSum implements Function {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		float result = 0.0f;
		boolean floatParams = false;

		for (Object i : args) {
			if (i instanceof Range) {
				i = eval((Range) i);
			}

			if (i instanceof Float) {
				floatParams = true;
			}

			if (i instanceof Number) {
				result = result + ((Number) i).floatValue();
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

	public Object eval(Range range) throws ParseException {
		float result = 0.0f;
		boolean floatParams = false;

		for (Cell i : range) {
			Object val = Parser.atomize(i.getString());

			if (val instanceof Float) {
				floatParams = true;
			}

			if (val instanceof Number) {
				result = result + ((Number) val).floatValue();
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
}

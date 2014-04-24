package language.Functions.Arithmatic;

import java.text.ParseException;
import java.util.LinkedList;

public class FuncMultiply extends ArithmaticFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		return simpleApply(new SimpleOpMultiply(), args);
	}

	private class SimpleOpMultiply implements SimpleOp {
		@Override
		public float eval(float a1, float a2) {
			return a1 * a2;
		}
	}
}
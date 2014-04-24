package language.Functions.Arithmatic;

import java.text.ParseException;
import java.util.LinkedList;

public class FuncAdd extends ArithmaticFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		return simpleApply(new SimpleOpAdd(), args);
	}

	private class SimpleOpAdd implements SimpleOp {
		@Override
		public float eval(float a1, float a2) {
			return a1 + a2;
		}
	}
}

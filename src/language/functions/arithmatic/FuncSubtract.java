package language.functions.arithmatic;

import java.util.LinkedList;

import language.exception.LangParseException;

public class FuncSubtract extends ArithmaticFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		return simpleApply(new SimpleOpSubtract(), args);
	}

	private class SimpleOpSubtract implements SimpleOp {
		@Override
		public float eval(float a1, float a2) {
			return a1 - a2;
		}
	}
}

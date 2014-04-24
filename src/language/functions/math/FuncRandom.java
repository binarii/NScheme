package language.functions.math;

import java.util.LinkedList;
import java.util.Random;

import language.exception.LangParseException;
import language.functions.BasicFunction;

public class FuncRandom extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		validateArgCount(args, 0);
		
		return new Random().nextInt();
	}
}

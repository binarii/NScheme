package language.Functions.math;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Random;

import language.Functions.BasicFunction;

public class FuncRandom extends BasicFunction {

	@Override
	public Object eval(LinkedList<Object> args) throws ParseException {
		validateArgCount(args, 0);
		
		return new Random().nextInt();
	}
}

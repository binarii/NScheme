package language.functions;

import java.text.ParseException;
import java.util.LinkedList;

public interface Function {

	public Object eval(LinkedList<Object> args) throws ParseException;
}

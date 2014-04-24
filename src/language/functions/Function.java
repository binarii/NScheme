package language.functions;

import java.util.LinkedList;

import language.exception.LangParseException;

public interface Function {

	public Object eval(LinkedList<Object> args) throws LangParseException;
}

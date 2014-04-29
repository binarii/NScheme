package language.functions;

import java.util.LinkedList;

import language.exception.LangParseException;

/**
 * A general interface for functions within the language. They are passed a list
 * of arguments that a already computed.
 */
public interface Function {

	public Object eval(LinkedList<Object> args) throws LangParseException;
}

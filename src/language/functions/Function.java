package language.functions;

import language.LangUtil;

/**
 * A general interface for functions within the language. They are passed a list
 * of arguments that a already computed.
 */
public abstract class Function extends LangUtil {

	public abstract Object apply(Object args);
}

package language.exception;

public class ArgumentTypeException extends LangParseException {

	public ArgumentTypeException(Class exp, Class given, int argOffset) {
		super("ERROR: Argument type mismatch. Expected " + exp.getCanonicalName() + 
				" Given: " + given.getCanonicalName());
	}
}

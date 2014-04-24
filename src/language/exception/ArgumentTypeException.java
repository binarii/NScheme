package language.exception;

public class ArgumentTypeException extends LangParseException {

	public ArgumentTypeException(Class t1, Class t2, int argOffset) {
		super("ERROR: Argument type mismatch. Expected " + t1.getCanonicalName() + 
				" Given: " + t1.getCanonicalName());
	}
}

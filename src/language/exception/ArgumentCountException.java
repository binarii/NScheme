package language.exception;

public class ArgumentCountException extends LangParseException {

	public ArgumentCountException(int expected, int given) {
		super("ERROR: Argument count mismatch. Expected: " + expected + " Given: " + given);
	}
}

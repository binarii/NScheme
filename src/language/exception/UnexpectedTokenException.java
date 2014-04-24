package language.exception;

public class UnexpectedTokenException extends LangParseException {

	public UnexpectedTokenException(String token) {
		super("ERROR: Unexpected token: " + token);
	}
}

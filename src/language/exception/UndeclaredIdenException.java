package language.exception;

public class UndeclaredIdenException extends LangParseException {

	public UndeclaredIdenException(String identifier) {
		super("ERROR: Identifier not found: " + identifier);
	}
}

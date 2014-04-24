package language.exception;

public class EOFException extends LangParseException {
	
	public EOFException() {
		super("ERROR: unexpected EOF found");
	}
}

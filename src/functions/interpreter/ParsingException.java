package functions.interpreter;

public class ParsingException extends RuntimeException {
	private static final long serialVersionUID = -8424313968652550579L;

	public ParsingException(int pos, String msg) {
		super(String.format("Parsing failed at index %d: %s", pos, msg));
	}
}
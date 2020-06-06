package functions.interpreter;

public class Token {
	public final String value;
	public final Type type;
	
	public Token(Type type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public Token(Type type) {
		this(type, type.toString());
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", type, value);
	}
}

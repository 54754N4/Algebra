package functions.interpreter;

import functions.model.MathFactory;

/** grammar
input: 			var_def EQUAL expresions
var_def: 		LEFT_PAREN? var (COMMA var)* RIGHT_PAREN?
expressions: 	LEFT_PAREN? expression (COMMA expression)* RIGHT_PAREN?
expression:		term ((PLUS|MINUS) term)*
term: 			factor ((MULTIPLY|DIVIDE) factor)*
factor: 		(PLUS|MINUS) factor | power
power: 			atom [POWER factor]
atom: 			func_expr
                | LEFT_PAREN expression RIGHT_PAREN
                | number
                | constant
func_expr:     func_name LEFT_PAREN expression RIGHT_PAREN

number:			[0-9]+("." [0-9]+)? 
func_name:		("EXP"|"LN"|"LOG"|"SQRT"|"COS"|"SIN"|"TAN"|"ABS"|...)
var:			[a-zA-Z]+
constant:		("PI" | "E")
 */
public class Lexer {
	private static final char NULL_CHAR = '\0';
	private final String input;
	private char current;
	private int pos;
	
	public Lexer(String input) {
		this.input = input+NULL_CHAR;
		current = input.charAt(pos = 0);
	}
	
	public void cry(String msg) {
		throw new ParsingException(pos, msg);
	}
	
	public boolean canAdvance() {
		return current != NULL_CHAR;
	}
	
	private void advance() {
		if (canAdvance())
			current = input.charAt(++pos);
	}
	
	private void advance(int i) {
		while (i-->0) advance();
	}
	
	private boolean peek(String target) {
		return input.substring(pos).toLowerCase().startsWith(target);
	}
	
	private boolean isSpace() {
		return current == ' ';
	}
	
	private boolean isDot() {
		return current == '.';
	}
	
	private boolean isDigit() {
		return Character.isDigit(current);
	}
	
	private boolean isLetter() {
		return Character.isLetter(current);
	}
	
	private void skipWhiteSpace() {
		while (isSpace()) advance();
	}
	
	private double number() {
		StringBuilder result = new StringBuilder();
		boolean foundDot = false;
		while ((isDigit() || isDot()) && canAdvance()) {
			if (isDot() && foundDot)
				cry("There should only be 1 dot in a number");
			else if (isDot())
				foundDot = true;
			result.append(current);
			advance();
		}
		return Double.parseDouble(result.toString());
	}
	
	private Token matchWords(String[] words, Type target) {
		for (String word : words) {
			if (peek(word)) {
				advance(word.length());
				return new Token(target, word);
			}
		}
		return null;
	}
	
	// Custom methods
	
	private Token matchFunction() {
		return matchWords(MathFactory.getFunctionNames(), Type.FUNCTION);
	}
	
	private Token matchConstant() {
		return matchWords(new String[] {"pi", "e"}, Type.CONSTANT);
	}
	
	private Token matchVariable() {
		StringBuilder sb = new StringBuilder();
		while (isLetter()) {
			sb.append(current);
			advance();
		}
		return new Token(Type.VARIABLE, sb.toString());
	}
	
	private Token matchWords() {
		Token token = matchFunction();	 
		if (token == null)	// if it's not a function
			token = matchConstant();
		if (token == null)	// if it's not a constant
			token = matchVariable();
		return token;
	}

	public Token getNextToken() throws ParsingException {
		while (canAdvance()) {
			if (isSpace()) {
				skipWhiteSpace();
				continue;
			} else if (isLetter()) return matchWords();
			else if (isDigit()) return new Token(Type.NUMBER, ""+number());
			else switch (current) {
				case '+': advance(); return new Token(Type.PLUS);
				case '-': advance(); return new Token(Type.MINUS);
				case '*': advance(); return new Token(Type.MULTIPLY);
				case '/': advance(); return new Token(Type.DIVIDE);
				case '(': advance(); return new Token(Type.LEFT_PAREN);
				case ')': advance(); return new Token(Type.RIGHT_PAREN);
				case ',': advance(); return new Token(Type.COMMA);
				default: cry("Unrecognised character '"+current+"'");
			}
		} return new Token(Type.EOF);
	}
	
	public static void spitTokens(String input) {
		Lexer l = new Lexer(input);
		Token token;
		while ((token = l.getNextToken()).type != Type.EOF) 
			System.out.println(token);
		System.out.println();
	}
	
	public static void main(String[] args) {
		String[] inputs = {"2*x+sqrt(3*y)+6, x+y*cos(exp(ln(z)))"}; //{"2+3", "10+cos(x)", "2*(cos(x)+sin(y))"};
		for (String input : inputs)
			spitTokens(input);
	}
}

package functions.interpreter;

import java.util.ArrayList;
import java.util.List;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.MultivariateExpression;

import static functions.model.MathFactory.*;

/** Parser grammar:
expressions: 	expression (COMMA expression)*
expression:		term ((PLUS|MINUS) term)*
term: 			factor ((MULTIPLY|DIVIDE) factor)*
factor: 		(PLUS|MINUS) factor | power
power: 			atom [POWER factor]
atom: 			LEFT_PAREN expression RIGHT_PAREN
				| FUNCTION LEFT_PAREN expression RIGHT_PAREN
                | CONSTANT
                | VARIABLE
                | NUMBER

Lexor constructs:
NUMBER:			[0-9]+("." [0-9]+)? 
FUNCTION:		("EXP"|"LN"|"LOG"|"SQRT"|"COS"|"SIN"|"TAN"|"ABS"|...)
VARIABLE:			[a-zA-Z]+
CONSTANT:		("PI" | "E")
 */
public class Parser {
	private Lexer lexer;
	private Token current;
	
	public Parser(String text) {
		lexer = new Lexer(text);
		current = lexer.getNextToken();
	}
	
	private void consume(Type type) throws ParsingException {
		if (current.type == type) 
			current = lexer.getNextToken();
		else 
			lexer.cry("Expected a token of type: "+type);
	}
	
	private boolean isType(Type...types) {
		for (Type type: types)
			if (current.type == type)
				return true;
		return false;
	}

	/**
	 * atom: 	LEFT_PAREN expression RIGHT_PAREN
				| FUNCTION LEFT_PAREN expression RIGHT_PAREN
                | CONSTANT
                | VARIABLE
                | NUMBER	*/
	private Expression atom() {
		Expression expression = null;
		Token token = this.current;
		if (isType(Type.LEFT_PAREN)) {
			consume(Type.LEFT_PAREN);
			expression = expression();
			consume(Type.RIGHT_PAREN);
		} else if (isType(Type.FUNCTION)) {
			consume(Type.FUNCTION);
			consume(Type.LEFT_PAREN);
			expression = expression();
			consume(Type.RIGHT_PAREN);
			expression = MathFactory.function(token.value, expression);  
		} else if (isType(Type.CONSTANT)) {
			consume(Type.CONSTANT);
			expression = (token.value.equals("pi")) ? PI : E; 
		} else if (isType(Type.VARIABLE)) {
			consume(Type.VARIABLE);
			expression = variable(token.value);
		} else if (isType(Type.NUMBER)) {
			consume(Type.NUMBER);
			expression = constant(Double.parseDouble(token.value));
		} else
			lexer.cry("Unrecognised atom: "+token);
		return expression;
	}
	
	// power: 			atom [POWER factor]
	private Expression power() {
		Expression atom = atom();
		if (isType(Type.POWER)) {
			consume(Type.POWER);
			atom = MathFactory.power(atom, factor());
		} return atom;
	}
	
	// factor: 		(PLUS|MINUS) factor | power
	private Expression factor() {
		if (isType(Type.PLUS, Type.MINUS)) {
			if (current.type == Type.PLUS) {
				consume(Type.PLUS);
				return factor();
			} else
				return negate(factor());
		} else 
			return power();
	}
	
	// term: 			factor ((MULTIPLY|DIVIDE) factor)*
	private Expression term() {
		Expression factor = factor();
		while (isType(Type.MULTIPLY, Type.DIVIDE)) {
			if (current.type == Type.MULTIPLY) {
				consume(Type.MULTIPLY);
				factor = product(factor, factor());
			} else if (current.type == Type.DIVIDE) {
				consume(Type.DIVIDE);
				factor = division(factor, factor());
			}
		}
		return factor;
	}
	
	// expression:		term ((PLUS|MINUS) term)*
	private Expression expression() {
		Expression term = term();
		while (isType(Type.PLUS, Type.MINUS)) {
			if (current.type == Type.PLUS) {
				consume(Type.PLUS);
				term = addition(term, term());
			} else if (current.type == Type.MINUS) {
				consume(Type.MINUS);
				term = substract(term, term());
			}
		}
		return term;
	}
	
	// expressions: 	expression (COMMA expression)*
	private MultivariateExpression expressions() {
		List<Expression> expressions = new ArrayList<>();
		expressions.add(expression());
		while (isType(Type.COMMA)) {
			consume(Type.COMMA);
			expressions.add(expression());
		}
		return new MultivariateExpression(expressions);
	}
	
	public MultivariateExpression parse() {
		return expressions();
	}
	
}

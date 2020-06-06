package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Abs extends UnaryOperator {

	public Abs(Expression expression) {
		super(expression, "|");
	}
	
	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.abs(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(|g(x)|) = (g(x)g'(x))/|g(x)|
		return MathFactory.division(
				MathFactory.product(expression, expression.derivative(name)), 
				MathFactory.abs(expression));
	}
	
	@Override
	public String toString() {
		return "|"+expression+"|";
	}

	@Override
	public Expression copy() {
		return MathFactory.abs(expression);
	}
}

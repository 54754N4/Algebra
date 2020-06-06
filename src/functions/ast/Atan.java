package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Atan extends UnaryOperator {

	public Atan(Expression expression) {
		super(expression, "atan");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.atan(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) {	// d/dx(atan(g(x))) = g'(x)/(1 + g(x)^2)
		return MathFactory.division(
				expression.derivative(name),
				MathFactory.addition(
						MathFactory.constant(1), 
						MathFactory.power(expression, MathFactory.constant(2))));
	}

	@Override
	public Expression copy() {
		return MathFactory.atan(expression);
	}
}

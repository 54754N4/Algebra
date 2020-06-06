package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Acos extends UnaryOperator {

	public Acos(Expression expression) {
		super(expression, "acos");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.acos(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(acos(g(x))) = -g'(x)/sqrt(1-g(x)^2)
		return MathFactory.negate(
				MathFactory.division(
					expression.derivative(name), 
					MathFactory.sqrt(
							MathFactory.substract(
									MathFactory.constant(1), 
									MathFactory.power(expression, MathFactory.constant(2))))));
	}

	@Override
	public Expression copy() {
		return MathFactory.acos(expression);
	}
}

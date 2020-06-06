package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Tan extends UnaryOperator {

	public Tan(Expression expression) {
		super(expression, "tan");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.tan(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(tan(g(x))) = g'(x)/cos(g(x))^2
		return MathFactory.division(
				expression.derivative(name),
				MathFactory.power(
						MathFactory.cos(expression), 
						MathFactory.constant(2)));
	}
	
	@Override
	public Expression copy() {
		return MathFactory.tan(expression);
	}
}

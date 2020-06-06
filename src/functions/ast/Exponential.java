package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Exponential extends UnaryOperator {

	public Exponential(Expression expression) {
		super(expression, "exp");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.exp(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(e^(g(x))) = e^(g(x)).g'(x)
		return MathFactory.product(
				MathFactory.exp(expression), 
				expression.derivative(name));
	}

	@Override
	public Expression copy() {
		return MathFactory.exp(expression);
	}
}

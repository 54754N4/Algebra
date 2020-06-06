package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Negate extends UnaryOperator {

	public Negate(Expression expression) {
		super(expression, "-");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return -expression.forward(variables, x);
	}

	@Override
	public Expression derivative(String name) {	// d/dx(-g(x)) = -g'(x)
		return MathFactory.negate(expression.derivative(name));
	}

	@Override
	public Expression copy() {
		return MathFactory.negate(expression);
	}
}

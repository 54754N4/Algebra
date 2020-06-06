package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Cos extends UnaryOperator {

	public Cos(Expression expression) {
		super(expression, "cos");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.cos(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(cos(g(x))) = -sin(g(x)).g'(x)
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.sin(expression), 
						expression.derivative(name)));
	}

	@Override
	public Expression copy() {
		return MathFactory.cos(expression);
	}
}

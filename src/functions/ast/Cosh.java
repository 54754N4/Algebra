package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Cosh extends UnaryOperator {

	public Cosh(Expression expression) {
		super(expression, "cosh");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.cosh(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String wrt) {	// d/dx(cosh(g(x))) = sinh(g(x))g'(x)
		return MathFactory.product(
				MathFactory.sinh(expression), 
				expression.derivative(wrt));
	}

	@Override
	public Expression copy() {
		return MathFactory.cosh(expression);
	}
}

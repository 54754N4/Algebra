package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Sinh extends UnaryOperator {

	public Sinh(Expression expression) {
		super(expression, "sinh");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.sinh(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String wrt) {	// d/dx(sinh(g(x))) = cosh(g(x))g'(x)
		return MathFactory.product(MathFactory.cosh(expression), expression.derivative(wrt));
	}

	@Override
	public Expression copy() {
		return MathFactory.sinh(expression);
	}

}

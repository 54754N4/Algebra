package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Sech extends UnaryOperator {

	public Sech(Expression expression) {
		super(expression, "sech");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}
	
	public static double of(double x) {
		return 1/Math.cosh(x);
	}

	@Override
	public Expression derivative(String wrt) {	// d/dx(sech(g(x))) = -tanh(g(x))sech(g(x))g'(x)
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.tanh(expression),
						MathFactory.product(
								MathFactory.sech(expression), 
								expression.derivative(wrt))));
	}

	@Override
	public Expression copy() {
		return MathFactory.sech(expression);
	}

}

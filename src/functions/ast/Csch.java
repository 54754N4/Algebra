package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Csch extends UnaryOperator {

	public Csch(Expression expression) {
		super(expression, "csch");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}

	public static double of(double x) {
		return 1/Math.sinh(x);
	}
	
	@Override
	public Expression derivative(String wrt) {	// d/dx(csch(g(x))) = -coth(g(x))csch(g(x))g'(x)
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.coth(expression),
						MathFactory.product(
								MathFactory.csch(expression), 
								expression.derivative(wrt))));
	}

	@Override
	public Expression copy() {
		return MathFactory.csch(expression);
	}

}

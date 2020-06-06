package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Coth extends UnaryOperator {

	public Coth(Expression expression) {
		super(expression, "coth");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}
	
	public static double of(double x) {
		return Math.cosh(x)/Math.sinh(x);
	}

	@Override
	public Expression derivative(String wrt) { // d/dx(coth(g(x))) = -csch(g(x))^2.g'(x)
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.power(MathFactory.csch(expression), MathFactory.constant(2)), 
						expression.derivative(wrt)));
	}

	@Override
	public Expression copy() {
		return MathFactory.coth(expression);
	}

}

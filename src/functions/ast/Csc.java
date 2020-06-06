package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Csc extends UnaryOperator {

	public Csc(Expression expression) {
		super(expression, "csc");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}

	public static double of(double x) {
		return 1/Math.sin(x);
	}
	
	@Override
	public Expression derivative(String wrt) {	// d/dx(csc(g(x))) = -csc(g(x))cot(g(x))g'(x)
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.csc(expression),
						MathFactory.product(
								MathFactory.cot(expression), 
								expression.derivative(wrt))));
	}

	@Override
	public Expression copy() {
		return MathFactory.csc(expression);
	}

}

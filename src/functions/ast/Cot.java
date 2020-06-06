package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Cot extends UnaryOperator {

	public Cot(Expression expression) {
		super(expression, "cot");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}

	public static double of(double x) {
		return Math.cos(x)/Math.sin(x);
	}
	
	@Override
	public Expression derivative(String name) { // d/dx(cot(g(x))) = -csc(g(x))^2.g'(x) 
		return MathFactory.negate(
				MathFactory.product(
						MathFactory.power(
								MathFactory.csc(expression), 
								MathFactory.constant(2)), 
						expression.derivative(name)));
	}

	@Override
	public Expression copy() {
		return MathFactory.cot(expression);
	}
}

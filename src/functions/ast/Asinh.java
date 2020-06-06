package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Asinh extends UnaryOperator {

	public Asinh(Expression expression) {
		super(expression, "asinh");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}
	
	public static double of(double x) {
		return Math.log(x + Math.sqrt(x*x+1));
	}

	@Override
	public Expression derivative(String name) { // d/dx(asinh(g(x))) = g'(x)/sqrt(g(x)^2+1)
		return MathFactory.division(
				expression.derivative(name), 
				MathFactory.sqrt(
						MathFactory.addition(
								MathFactory.power(expression, MathFactory.constant(2)), 
								MathFactory.constant(1))));
	}

	@Override
	public Expression copy() {
		return MathFactory.asinh(expression);
	}
}

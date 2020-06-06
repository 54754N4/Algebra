package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Atanh extends UnaryOperator {

	public Atanh(Expression expression) {
		super(expression, "atanh");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}
	
	public static double of(double x) {
		return 1d/2*Math.log((1+x)/(1-x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(atanh(g(x))) = g'(x)/(1-g(x)^2)
		return MathFactory.division(
				expression.derivative(name), 
				MathFactory.substract(
						MathFactory.constant(1), 
						MathFactory.power(expression, MathFactory.constant(2))));
	}

	@Override
	public Expression copy() {
		return MathFactory.atanh(expression);
	}
}

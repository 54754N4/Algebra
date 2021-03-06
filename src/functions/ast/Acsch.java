package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Acsch extends UnaryOperator {

	public Acsch(Expression expression) {
		super(expression, "acsch");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}
	
	public static double of(double x) {
		return 1d/2*Math.log(1/x+Math.sqrt(1/(x*x)-1));
	}

	@Override
	public Expression derivative(String name) { // d/dx(acos(g(x))) = -g'(x)/sqrt(1-g(x)^2)
		return MathFactory.division(
				expression.derivative(name), 
				MathFactory.product(
						MathFactory.abs(expression), 
						MathFactory.sqrt(
								MathFactory.substract(
										MathFactory.constant(1), 
										MathFactory.power(expression, MathFactory.constant(2))))));
	}

	@Override
	public Expression copy() {
		return MathFactory.acsch(expression);
	}
}

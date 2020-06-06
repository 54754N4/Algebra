package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Asin extends UnaryOperator {

	public Asin(Expression expression) {
		super(expression, "asin");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.asin(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) {	// d/dx(asin(g(x))) = g'(x)/sqrt(1-g(x)^2)
		return MathFactory.division(
				expression.derivative(name), 
				MathFactory.sqrt(
						MathFactory.substract(
								MathFactory.constant(1), 
								MathFactory.power(expression, MathFactory.constant(2)))));
	}

	@Override
	public Expression copy() {
		return MathFactory.asin(expression);
	}
}

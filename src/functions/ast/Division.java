package functions.ast;

import java.util.Set;

import functions.model.BinaryOperator;
import functions.model.Expression;
import functions.model.MathFactory;

public class Division extends BinaryOperator {

	public Division(Expression left, Expression right) {
		super(left, "/", right);
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return left.forward(variables, x) / right.forward(variables, x);
	}

	@Override
	public Expression derivative(String name) { // d/dx(f/g)(x) = (g(x).f'(x)-f(x)g'(x))/g(x)^2
		return MathFactory.division(
				MathFactory.substract(
						MathFactory.product(left.derivative(name), right), 
						MathFactory.product(left, right.derivative(name))), 
				MathFactory.power(right, MathFactory.constant(2)));
	}

	@Override
	public Expression copy() {
		return MathFactory.division(left, right);
	}
}

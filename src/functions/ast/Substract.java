package functions.ast;

import java.util.Set;

import functions.model.BinaryOperator;
import functions.model.Expression;
import functions.model.MathFactory;

public class Substract extends BinaryOperator {

	public Substract(Expression left, Expression right) {
		super(left, "-", right);
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return left.forward(variables, x) - right.forward(variables, x);
	}

	@Override
	public Expression derivative(String name) { // d/dx(f-g)(x) = f'(x)-g'(x)
		return MathFactory.substract(left.derivative(name), right.derivative(name));
	}

	@Override
	public Expression copy() {
		return MathFactory.substract(left, right);
	}
}

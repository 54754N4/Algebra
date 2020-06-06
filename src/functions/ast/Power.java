package functions.ast;

import java.util.Set;

import functions.model.BinaryOperator;
import functions.model.Expression;
import functions.model.MathFactory;

public class Power extends BinaryOperator {

	public Power(Expression left, Expression right) {
		super(left, "^", right);
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.pow(left.forward(variables, x), right.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(f(x)^g(x)) = f(x)^g(x) . [g'(x).ln(f(x)) + (g(x).f'(x))/f(x)]
		Expression first = MathFactory.power(left, right),
			second = MathFactory.addition(
					MathFactory.product(right.derivative(name), MathFactory.ln(left)),
					MathFactory.division(
							MathFactory.product(right, left.derivative(name)), left));
		return MathFactory.product(first, second);
	}

	@Override
	public Expression copy() {
		return MathFactory.power(left, right);
	}
}

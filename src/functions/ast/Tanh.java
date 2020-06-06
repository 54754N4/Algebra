package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Tanh extends UnaryOperator {

	public Tanh(Expression expression) {
		super(expression, "tanh");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.tanh(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String wrt) {  // d/dx(tanh(g(x))) = g'(x)/cosh(g(x))^2
		return MathFactory.division(
				expression.derivative(wrt), 
				MathFactory.power(
						MathFactory.cosh(expression), 
						MathFactory.constant(2)));
	}

	@Override
	public Expression copy() {
		return MathFactory.tanh(expression);
	}

}

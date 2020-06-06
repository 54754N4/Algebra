package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Sqrt extends UnaryOperator {

	public Sqrt(Expression expression) {
		super(expression, "sqrt");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.sqrt(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) { // d/dx(sqrt(g(x))) = g'(x)/(2.sqrt(g(x))) 
		return MathFactory.division(
				expression.derivative(name),
				MathFactory.product(
						MathFactory.constant(2), 
						MathFactory.sqrt(expression)));
	}

	@Override
	public Expression copy() {
		return MathFactory.sqrt(expression);
	}
	
	
}

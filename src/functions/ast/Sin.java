package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Sin extends UnaryOperator {

	public Sin(Expression expression) {
		super(expression, "sin");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.sin(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) {	// d/dx(sin(g(x))) = cos(g(x)).g'(x) 
		return MathFactory.product(MathFactory.cos(expression), expression.derivative(name));
	}

	@Override
	public Expression copy() {
		return MathFactory.sin(expression);
	}
}

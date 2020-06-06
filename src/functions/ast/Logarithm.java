package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Logarithm extends UnaryOperator {

	public Logarithm(Expression expression) {
		super(expression, "ln");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Math.log(expression.forward(variables, x));
	}

	@Override
	public Expression derivative(String name) {	// d/dx(ln(g(x))) = g'(x)/g(x)
		return MathFactory.division(expression.derivative(name), expression);
	}

	@Override
	public Expression copy() {
		return MathFactory.ln(expression);
	}
}

package functions.ast;

import java.util.Set;

import functions.model.Expression;
import functions.model.MathFactory;
import functions.model.UnaryOperator;

public class Sec extends UnaryOperator {

	public Sec(Expression expression) {
		super(expression, "sec");
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return of(expression.forward(variables, x));
	}

	public static double of(double x) {
		return 1/Math.cos(x);
	}
	
	@Override
	public Expression derivative(String name) { // d/dx(sec(g(x))) = sec(g(x)).tan(g(x)).g'(x) 
		return MathFactory.product(
				MathFactory.product(
						MathFactory.sec(expression), 
						MathFactory.tan(expression)), 
				expression.derivative(name));
	}

	@Override
	public Expression copy() {
		return MathFactory.sec(expression);
	}
}

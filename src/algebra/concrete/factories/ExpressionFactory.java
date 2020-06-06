package algebra.concrete.factories;

import algebra.factory.UnitaryRingFactory;
import functions.model.Expression;
import functions.model.MultivariateExpression;

import static functions.model.MathFactory.*;

public class ExpressionFactory implements UnitaryRingFactory<Expression> {
	private static final ExpressionFactory INSTANCE = new ExpressionFactory();
	
	public static ExpressionFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Expression getAdditiveIdentity() {
		return constant(0);
	}

	@Override
	public Expression getMultiplicativeIdentity() {
		return constant(1);
	}
	
	@Override
	public Expression parse(String input) {
		return MultivariateExpression.parse(input).asExpression();
	}

}

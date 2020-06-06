package functions.model;

public abstract class UnaryOperator extends Expression {
	public final Expression expression;
	
	public UnaryOperator(Expression expression, String token) {
		super(token);
		this.expression = expression;
	}
	
	@Override
	public boolean equals(Expression o) {
		if (!getClass().isInstance(o))
			return false;
		return getClass().cast(o).expression.equals(expression);
	}

	@Override
	public String toString() {
		return getToken() + "(" + expression + ")";
	}
}

package functions.model;

public abstract class BinaryOperator extends Expression {
	public final Expression left, right;
	
	public BinaryOperator(Expression left, String token, Expression right) {
		super(token);
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean equals(Expression o) {
		if (!getClass().isInstance(o))
			return false;
		BinaryOperator op = getClass().cast(o); 
		return op.left.equals(left) && op.right.equals(right);
	}
	
	@Override
	public String toString() {
		return "(" + left + getToken() + right + ")";
	}
}

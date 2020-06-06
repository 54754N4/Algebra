package functions.model;

import java.util.Set;

public class Constant extends Expression {
	public final double value;
	
	public Constant(double i) {
		super(Double.toString(i));
		this.value = i;
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		return Double.parseDouble(getToken());
	}

	@Override
	public Expression derivative(String name) {
		return new Constant(0);
	}

	@Override
	public String toString() {
		return getToken();
	}

	@Override
	public Expression copy() {
		return MathFactory.constant(value);
	}

	@Override
	public boolean equals(Expression k) {
		if (!getClass().isInstance(k))
			return false;
		return getClass().cast(k).value == value;
	}
}

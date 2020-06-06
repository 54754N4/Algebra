package functions.model;

import static functions.model.MathFactory.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Variable extends Expression {

	public Variable(String name) {
		super(name);
	}

	@Override
	public double forward(Set<String> variables, double...x) {
		List<String> vars = new ArrayList<>(variables);
		int index = vars.indexOf(getToken()); 
		if (index != -1)
			return x[index]; 
		throw new IllegalStateException("Variable not found in expression: "+getToken());
	}

	@Override
	public Expression derivative(String name) {
		return name.equals(getToken()) ? constant(1) : constant(0);	
	}
	
	@Override
	public String toString() {
		return getToken();
	}

	@Override
	public Expression copy() {
		return MathFactory.variable(getToken());
	}

	@Override
	public boolean equals(Expression k) {
		if (!Variable.class.isInstance(k))
			return false;
		return Variable.class.cast(k).getToken().equals(getToken());
	}
}

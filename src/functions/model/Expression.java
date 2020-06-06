package functions.model;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import algebra.concrete.factories.ExpressionFactory;
import algebra.fields.DivisionElement;
import functions.tree.ExpressionTreeBuilder;
import functions.tree.Tree;

public abstract class Expression implements DivisionElement<Expression> {
	protected String token;
	
	public Expression(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public String[] getVariables() {
		return scanForVariables().toArray(new String[0]);
	}
	
	public Set<String> scanForVariables() {
		Set<String> variables = new TreeSet<>();
		VariablesScanner.scan(this, variables);
		return variables;
	}
	
	public double of(double...vars) {
		Set<String> variables = scanForVariables();
		if (vars.length != variables.size())
			throw new IllegalArgumentException(
					"I need " + variables.size()
					+ " inputs for each " + Arrays.toString(variables.toArray())
					+ " variable respectively."); 
		return forward(variables, vars);
	}
	
	public abstract double forward(Set<String> variables, double...x);
	public abstract Expression derivative(String wrt);
	
	public Tree<Expression> asTree() {
		return ExpressionTreeBuilder.getTreeBuilder().build(this);
	}
	
	@Override
	public boolean isAdditivelyCommutative() {
		return true;
	}

	@Override
	public boolean isAdditiveIdentity() {
		return equals(ExpressionFactory.getInstance().getAdditiveIdentity());
	}
	
	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		return equals(ExpressionFactory.getInstance().getMultiplicativeIdentity());
	}
	
	@Override
	public Expression plus(Expression k) {
		return MathFactory.addition(this, k);
	}

	@Override
	public Expression times(double lambda) {
		return MathFactory.product(this, MathFactory.constant(lambda));
	}

	@Override
	public Expression times(Expression k) {
		return MathFactory.product(this, k);
	}

	@Override
	public Expression power(int degree) {
		return MathFactory.power(this,  MathFactory.constant(degree));
	}

	@Override
	public double modulus() {
		throw new ModulusUndefinedException();
	}

	@Override
	public Expression inverse() {
		return MathFactory.division(MathFactory.constant(1), this);
	} 
	
	public static class ModulusUndefinedException extends ArithmeticException {
		private static final long serialVersionUID = 8897409889611853977L;

		public ModulusUndefinedException() {
			super("The modulus/norm is undefined for an expression");
		}
	}
}

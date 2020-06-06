package algebra.concrete;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algebra.fields.DivisionElement;

public class MultivariatePolynomial<K extends DivisionElement<K>> implements DivisionElement<MultivariatePolynomial<K>> {
	private List<MultivariateMonomial<K>> monomials;
	
	public static class MultivariateMonomial<K> {
		private K coefficient;
		private List<Variable> variables;
		
		public MultivariateMonomial(K coefficient, List<Variable> variables) {
			this.coefficient = coefficient;
			this.variables = variables;
		}
		
		public int degree() {
			int sum = 0;
			for (Variable variable : variables) 
				sum += variable.power;
			return sum;
		}
		
		public MultivariateMonomial<K> addVariable(String name, int power) {
			variables.add(new Variable(name, power));
			return this;
		}
		
		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			variables.forEach((var) -> sb.append(var.toString()));
			return String.format("%s%s", coefficient.toString(), sb.toString());
		}
	}
	
	public static class Variable {
		public final String name;
		public final int power;
		
		public Variable(String name, int power) {
			this.name = name;
			this.power = power;
		}
		
		@Override
		public int hashCode() {
			return name.hashCode(); 
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Variable))
				return false;
			Variable v = (Variable) o;
			return name.equals(v.name);
		}
		
		@Override
		public String toString() {
			return String.format("%s^%d", name, power);
		}
	}
	
	public MultivariatePolynomial(List<MultivariateMonomial<K>> monomials) {
		this.monomials = monomials;
	}
	
	public int getVariablesCount() {
		Set<Variable> independent = new HashSet<>();
		for (MultivariateMonomial<K> monomial : monomials)
			for (Variable var : monomial.variables)
				independent.add(var);
		return independent.size();
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultivariatePolynomial<K> times(MultivariatePolynomial<K> k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMultiplicatevlyCommutative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultivariatePolynomial<K> plus(MultivariatePolynomial<K> k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultivariatePolynomial<K> times(double lambda) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAdditivelyCommutative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdditiveIdentity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultivariatePolynomial<K> copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double modulus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(MultivariatePolynomial<K> k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultivariatePolynomial<K> inverse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String current;
		for (MultivariateMonomial<?> monomial : monomials) {
			current = monomial.toString();
			if (current.startsWith("-"))
				sb.append(current);
			else 
				sb.append(current).append("+");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}

	@Override
	public MultivariatePolynomial<K> power(int degree) {
		// TODO Auto-generated method stub
		return null;
	}

}

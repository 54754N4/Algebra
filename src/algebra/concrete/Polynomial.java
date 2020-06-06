package algebra.concrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import algebra.concrete.factories.PolynomialFactory;
import algebra.factory.UnitaryRingFactory;
import algebra.fields.DivisionElement;
import algebra.fields.UnitaryElement;

public class Polynomial<K extends DivisionElement<K>> implements UnitaryElement<Polynomial<K>> {
	private List<Monomial<K>> monomials;
	private PolynomialFactory<K> factory;
	private UnitaryRingFactory<K> subFactory;
	
	public static class Monomial<K extends DivisionElement<K>> 
			implements UnitaryElement<Monomial<K>>, Comparable<Monomial<K>> {
		private K coefficient;
		private int degree;
		
		public Monomial(K coefficient, int degree) {
			setCoefficient(coefficient);
			setDegree(degree);
		}
		
		public Monomial<K> setCoefficient(K coefficient) {
			this.coefficient = coefficient;
			return this;
		}
		
		public Monomial<K> setDegree(int degree) {
			this.degree = degree;
			return this;
		}
		
		@Override
		public String toString() {
			return String.format("%sx^%s", coefficient.toString(), degree);
		}
		
		@Override
		public boolean isAdditivelyCommutative() {
			return true;
		}

		@Override
		public boolean isAdditiveIdentity() {
			return coefficient.isAdditiveIdentity() && degree == 0;
		}
		
		@Override
		public boolean isMultiplicatevlyCommutative() {
			return true;
		}
		
		@Override
		public boolean isMultiplicativeIdentity() {
			return coefficient.isMultiplicativeIdentity() && degree == 0;
		}

		@Override
		public Monomial<K> plus(Monomial<K> k) {
			if (k.degree != degree)
				throw new IllegalArgumentException("Can't sum monomials of different degrees");
			return new Monomial<>(coefficient.plus(k.coefficient), degree);
		}

		@Override
		public Monomial<K> times(double lambda) {
			return new Monomial<>(coefficient.times(lambda), degree);
		}
		
		public Monomial<K> times(K k) {
			return new Monomial<>(coefficient.times(k), degree);
		}
		
		@Override
		public Monomial<K> power(int degree) {
			return new Monomial<>(coefficient.power(degree), this.degree*degree);
		}	
		
		@Override
		public Monomial<K> times(Monomial<K> k) {
			return new Monomial<>(coefficient.times(k.coefficient), degree+k.degree);
		}

		@Override
		public double modulus() {
			throw new ModulusUndefinedException();
		}

		@Override
		public int compareTo(Monomial<K> o) {
			if (degree < o.degree) return -1;
			else if (degree == o.degree) return 0;
			return 1;
		}
		
		@Override
		public Monomial<K> copy() {
			return new Monomial<>(coefficient, degree);
		}
		
		@Override
		public boolean equals(Monomial<K> k) {
			return coefficient.equals(k.coefficient) && degree == k.degree;
		}	
	}
	
	public Polynomial(List<Monomial<K>> monomials, PolynomialFactory<K> factory, UnitaryRingFactory<K> subFactory) {
		this.monomials = monomials;
		this.factory = factory;
		this.subFactory = subFactory;
	}
	
	public Polynomial(Monomial<K> monomial, PolynomialFactory<K> factory, UnitaryRingFactory<K> subFactory) {
		this(Arrays.asList(monomial), factory, subFactory);
	}
	
	public K evaluatedOn(K x) {
		K total = subFactory.getAdditiveIdentity();
		for (Monomial<K> m : monomials) 
			total = total.plus(m.coefficient.times(x.power(m.degree)));
		return total;
	}
	
	public Polynomial<K> removeZeros() {
		final List<Monomial<K>> removables = new ArrayList<>();
		monomials.stream()
			.filter(m -> m.coefficient.isAdditiveIdentity())
			.forEach(removables::add);
		for (Monomial<K> removable : removables)
			monomials.remove(removable);
		return this;
	}
	
	@Override
	public boolean isAdditivelyCommutative() {
		return true;
	}

	@Override
	public boolean isAdditiveIdentity() {
		return equals(factory.getAdditiveIdentity());
	}
	
	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		return equals(factory.getMultiplicativeIdentity());
	}

	@Override
	public Polynomial<K> plus(Polynomial<K> k) {
		List<Monomial<K>> monomials = new ArrayList<>();
		int i0 = 0, i1 = 0,
			l0 = this.monomials.size(), 
			l1 = k.monomials.size();	
		Monomial<K> m0, m1, current;
		while (i0 < l0 && i1 < l1) {
			m0 = this.monomials.get(i0);
			m1 = k.monomials.get(i1);
			if (m0.compareTo(m1) > 0) {
				current = m0.copy();
				i0++;
			} else if (m0.compareTo(m1) < 0) {
				current = m1.copy();
				i1++;
			} else {
				current = m0.plus(m1);
				i0++;
				i1++;
			}
			monomials.add(current);
		}
		while (i0 < l0 || i1 < l1) {	// add the rest
			if (i0 < l0) {
				current = this.monomials.get(i0).copy();
				i0++;
			} else {
				current = k.monomials.get(i1).copy();
				i1++;
			}
			monomials.add(current);
		}
		return new Polynomial<>(monomials, factory, subFactory).removeZeros();
	}

	@Override
	public Polynomial<K> times(double lambda) {
		final List<Monomial<K>> result = new ArrayList<>();
		monomials.forEach(m -> result.add(m.copy().setCoefficient(m.coefficient.times(lambda))));
		return new Polynomial<>(result, factory, subFactory).removeZeros();
	}
	
	public Polynomial<K> times(K k) {
		final List<Monomial<K>> result = new ArrayList<>();
		monomials.forEach(m -> result.add(m.copy().setCoefficient(m.coefficient.times(k))));
		return new Polynomial<>(result, factory, subFactory).removeZeros();
	}
	
	@Override
	public Polynomial<K> times(Polynomial<K> k) {
		Polynomial<K> result = factory.getAdditiveIdentity();
		for (Monomial<K> a : monomials)
			for (Monomial<K> b : k.monomials)
				result = result.plus(new Polynomial<>(a.times(b), factory, subFactory));
		return result.removeZeros();
	}
	
	@Override
	public Polynomial<K> power(int power) {
		Polynomial<K> result = factory.getMultiplicativeIdentity();
		while (power-- > 0)
			result = result.times(this);
		return result.removeZeros();
	}
	
	public Polynomial<K> compose(Polynomial<K> p) {
		Polynomial<K> result = factory.getAdditiveIdentity();
		for (Monomial<K> monomial : monomials)
			result = result.plus(p.power(monomial.degree).times(monomial.coefficient));
		return result.removeZeros();
	}
	
	@Override
	public Polynomial<K> copy() {
		final List<Monomial<K>> result = new ArrayList<>();
		monomials.forEach((m) -> result.add(m.copy()));
		return new Polynomial<>(result, factory, subFactory).removeZeros();
	}

	@Override
	public double modulus() {
		throw new ModulusUndefinedException();
	}

	@Override
	public boolean equals(Polynomial<K> k) {
		if (monomials.size() != k.monomials.size())
			return false;
		for (int i=0; i<monomials.size(); i++)
			if (!monomials.get(i).equals(k.monomials.get(i)))
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		if (monomials.size() == 0 
				|| (monomials.size() == 1 && monomials.get(0).isAdditiveIdentity()))
			return subFactory.getAdditiveIdentity().toString();
		StringBuilder sb = new StringBuilder();
		String current;
		removeZeros();
		Collections.sort(monomials);
		Monomial<?> first = monomials.get(0);
		for (Monomial<?> monomial : monomials) {
			current = monomial.toString();
			if (!monomial.equals(first) && !current.startsWith("-"))
				sb.append("+");
			sb.append(current);
		} 
		return sb.toString();
	}

	public static class ModulusUndefinedException extends ArithmeticException {
		private static final long serialVersionUID = 8897409889611853977L;

		public ModulusUndefinedException() {
			super("The modulus/norm is undefined for a polynomial");
		}
	} 
}

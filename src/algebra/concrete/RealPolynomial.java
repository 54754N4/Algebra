package algebra.concrete;

import algebra.fields.DivisionElement;

public class RealPolynomial implements DivisionElement<RealPolynomial> {
	private double[] coefficients;
	
	public static final RealPolynomial ZERO = new RealPolynomial(0),
			ONE = new RealPolynomial(1);
	
	public RealPolynomial(double...coefficients) {
		this.coefficients = coefficients;
	}

	@Override
	public boolean isAdditivelyCommutative() {
		return true;
	}
	
	@Override
	public boolean isAdditiveIdentity() {
		return equals(ZERO);
	}
	
	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		return equals(ONE);
	}

	@Override
	public RealPolynomial plus(RealPolynomial k) {
		double[] bigger = coefficients, 
				smaller = k.coefficients, 
				result;
		if (bigger.length < smaller.length) {
			bigger = k.coefficients;
			smaller = coefficients;
		}
		result = new double[bigger.length];
		for (int i=0; i<bigger.length; i++)
			result[i] = bigger[i] + ((i > smaller.length-1) ? 0 : smaller[i]);
		return new RealPolynomial(result);
	}

	@Override
	public RealPolynomial times(double lambda) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RealPolynomial times(RealPolynomial k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RealPolynomial inverse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public double modulus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RealPolynomial copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(RealPolynomial k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RealPolynomial power(int degree) {
		// TODO Auto-generated method stub
		return null;
	}

}

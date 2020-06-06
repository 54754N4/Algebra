package algebra.concrete;

import algebra.concrete.factories.RealFactory;
import algebra.fields.*;

public class Real extends Number implements LatticeElement<Real>, Comparable<Real> {
	private static final long serialVersionUID = -1212475019447572662L;

	private final double x;

	public static final RealFactory factory = RealFactory.getInstance();
	public static final Real ZERO = factory.getAdditiveIdentity(),
			ONE = factory.getMultiplicativeIdentity();
	
	public Real(double x) {
		this.x = x;
	}
	
	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
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
	public boolean isMultiplicativeIdentity() {
		return equals(ONE);
	}
	
	@Override
	public Real plus(Real a) {
		return new Real(x+a.x);
	}

	@Override
	public Real times(double lambda) {
		return new Real(x*lambda);
	}

	@Override
	public Real times(Real a) {
		return new Real(x*a.x);
	}
	
	@Override
	public Real power(int degree) {
		return new Real(Math.pow(x, degree));
	}

	@Override
	public Real inverse() {
		return new Real(1/x);
	}
	
	@Override
	public double modulus() {
		return Math.abs(x);
	}

	@Override
	public int compareTo(Real o) {
		if (x < o.x) return -1;
		else if (x == o.x) return 0;
		else return 1;
	}
	
	@Override
	public Real copy() {
		return new Real(x);
	}
	
	@Override
	public boolean equals(Real k) {
		return x == k.x;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Real) 
			return equals((Real) o);
		else if (o instanceof Number) 
			return x == ((Number) o).doubleValue(); 
		else return false;
	}
	
	@Override
	public String toString() {
		return String.format("%f", x);
	}

	public static void main(String[] args) {
		double x = 1.2, y = 2.3;
		Real rx = new Real(x), ry = new Real(y);
		System.out.println(rx.equals(x));
		System.out.println(rx.times(ry).equals(x*y));
		System.out.println(rx.plus(ry).equals(x+y));
		System.out.println(rx.minus(ry).equals(x-y));
		System.out.println(rx.divideBy(ry).equals(x/y));
	}

	@Override
	public int intValue() {
		return (int) x;
	}

	@Override
	public long longValue() {
		return (long) x;
	}

	@Override
	public float floatValue() {
		return (float) x;
	}

	@Override
	public double doubleValue() {
		return x;
	}	
}

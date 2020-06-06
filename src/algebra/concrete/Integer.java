package algebra.concrete;

import algebra.concrete.factories.IntegerFactory;
import algebra.fields.LatticeElement;

public class Integer extends Number implements LatticeElement<Integer>, Comparable<Integer> {
	private static final long serialVersionUID = -7581397723321403499L;
	
	public static final IntegerFactory factory = IntegerFactory.getInstance();
	public static final Integer ZERO = factory.getAdditiveIdentity(),
			ONE = factory.getMultiplicativeIdentity();
	
	private final int x;
	
	public Integer(int x) {
		this.x = x;
	}

	@Override
	public boolean isAdditiveIdentity() {
		return equals(ZERO);
	}
	
	@Override
	public boolean isAdditivelyCommutative() {
		return true;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		return equals(ONE);
	}

	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
	}

	@Override
	public Integer plus(Integer k) {
		return new Integer(x+k.x);
	}

	@Override
	public Integer times(double lambda) {
		return new Integer((int) (x*lambda));
	}

	@Override
	public Integer times(Integer k) {
		return new Integer(x*k.x);
	}
	
	@Override
	public Integer power(int degree) {
		Integer total = ONE;
		while (degree-->0)
			total = total.times(this);
		return total;
	}
	
	@Override
	public Integer inverse() {
		return new Integer(1/x);
	}
	
	@Override
	public Integer copy() {
		return new Integer(x);
	}

	@Override
	public double modulus() {
		return x;
	}

	@Override
	public boolean equals(Integer k) {
		return x == k.x;
	}

	@Override
	public int compareTo(Integer o) {
		if (x < o.x) return -1;
		else if (x == o.x) return 0;
		else return 1;
	}

	@Override
	public int intValue() {
		return x;
	}

	@Override
	public long longValue() {
		return x;
	}

	@Override
	public float floatValue() {
		return x;
	}

	@Override
	public double doubleValue() {
		return x;
	}
}

package algebra.concrete;

import algebra.concrete.RealComplex.InverseZeroComplexException;
import algebra.concrete.RealComplex.PhaseZeroComplexException;
import algebra.concrete.factories.ComplexFactory;
import algebra.fields.DivisionElement;

public class Complex<K extends DivisionElement<K>> implements DivisionElement<Complex<K>> {
	private K x, y;
	private ComplexFactory<K> factory;
	private Complex<K> ZERO, ONE;
	
	public Complex(K x, K y) {
		this(x, y, null);
	}
	
	public Complex(K x, K y, ComplexFactory<K> factory) {
		this.x = x;
		this.y = y;
		setFactory(factory);
	}
	
	public void setFactory(ComplexFactory<K> factory) {
		this.factory = factory;
	}
	
	public void update() {
		if (factory != null) {
			if (ZERO == null)
				ZERO = factory.getAdditiveIdentity();
			if (ONE == null)
				ONE = factory.getMultiplicativeIdentity();
		}
	}
	
	@Override
	public boolean isAdditivelyCommutative() {
		return true;
	}

	@Override
	public boolean isAdditiveIdentity() {
		update();
		return equals(ZERO);
	}

	@Override
	public boolean isMultiplicatevlyCommutative() {
		return true;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		update();
		return equals(ONE);
	}

	@Override
	public Complex<K> plus(Complex<K> k) {
		return new Complex<>(x.plus(k.x), y.plus(k.y), factory);
	}

	@Override
	public Complex<K> times(double lambda) {
		return new Complex<>(x.times(lambda), y.times(lambda), factory);
	}
	
	@Override
	public Complex<K> times(Complex<K> c) {
		return new Complex<>(
				(x.times(c.x)).minus(y.times(c.y)),
				(x.times(c.y)).plus(y.times(c.x)));
	}

	@Override
	public Complex<K> power(int degree) {
		Complex<K> total = factory.getMultiplicativeIdentity();
		while (degree-->0)
			total = total.times(this);
		return total;
	}
	
	@Override
	public Complex<K> inverse() {
		update();
		if (equals(ZERO))
			throw new InverseZeroComplexException();
		K denominator = (x.times(x)).plus(y.times(y));
		return new Complex<>(x.divideBy(denominator), y.times(-1).divideBy(denominator));
	}

	@Override
	public Complex<K> copy() {
		return new Complex<>(x.copy(), y.copy());
	}

	@Override
	public boolean equals(Complex<K> c) {
		return x.equals(c.x) && y.equals(c.y);
	}
	
	public K real() {
		return x;
	}
	
	public K imaginary() {
		return y;
	}
	
	public Complex<K> conjugate() {
		return new Complex<>(x, y.times(-1));
	}
	
	@Override
	public double modulus() {
		return Math.sqrt(x.modulus()*x.modulus()+y.modulus()*y.modulus());
	}
	
	public double phase() throws PhaseZeroComplexException{
		if (x.modulus()>0) 
			return Math.atan(y.divideBy(x).modulus());
		if (x.modulus()<0 && y.modulus()>=0) 
			return Math.atan(y.divideBy(x).modulus())+Math.PI;
		if (x.modulus()<0 && y.modulus()<0)
			return Math.atan(y.divideBy(x).modulus())-Math.PI;
		if (x.modulus()==0 && y.modulus()>0)
			return Math.PI/2;
		if (x.modulus()==0 && y.modulus()<0)
			return -Math.PI/2; 
		throw new PhaseZeroComplexException();			//else
	}
	
	@Override
	public String toString() {
		return String.format("%s + %si", x.toString(), y.toString());
	}
	
	public static void main(String[] args) {
		ComplexFactory<Real> factory = ComplexFactory.Type.ofReals();
		RealComplex r0 = new RealComplex(1,2), 
				r1 = new RealComplex(2,3);
		Complex<Real> c0 = new Complex<>(new Real(r0.real()), new Real(r0.imaginary()), factory),
				c1 = new Complex<>(new Real(r1.real()), new Real(r1.imaginary()), factory);
		System.out.println(r0.divideBy(r1));
		System.out.println(c0.divideBy(c1));
	}
}

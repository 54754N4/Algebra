package algebra.concrete;

import algebra.concrete.factories.RealQuaternionFactory;
import algebra.fields.DivisionElement;

public class RealQuaternion implements DivisionElement<RealQuaternion> {
	private final double a,b,c,d;

	public static RealQuaternionFactory factory = RealQuaternionFactory.getInstance();
	public static RealQuaternion ZERO = factory.getAdditiveIdentity(), 
			ONE = factory.getMultiplicativeIdentity(), 
			I = new RealQuaternion(0,1,0,0), 
			J = new RealQuaternion(0,0,1,0), 
			K = new RealQuaternion(0,0,0,1);
	
	public RealQuaternion(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
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
		return false;
	}
	
	@Override
	public boolean isMultiplicativeIdentity() {
		return equals(ONE);
	}

	@Override
	public RealQuaternion plus(RealQuaternion q) {
		return new RealQuaternion(a+q.a, b+q.b, c+q.c, d+q.d);
	}

	@Override
	public RealQuaternion times(double scalar) {
		return new RealQuaternion(scalar*a, scalar*b, scalar*c, scalar*d);
	}
	
	@Override
	public RealQuaternion times(RealQuaternion q) {
		return new RealQuaternion(
				a*q.a - b*q.b - c*q.c - d*q.d,
				a*q.b + b*q.a + c*q.d - d*q.c,
				a*q.c - b*q.d + c*q.a + d*q.b,
				a*q.d + b*q.c - c*q.b + d*q.a
		);
	}
	
	@Override
	public RealQuaternion power(int degree) {
		RealQuaternion total = ONE;
		while (degree-->0) 
			total = total.times(this);
		return total;
	}
	
	@Override
	public RealQuaternion inverse() {
		if (equals(ZERO))
			throw new InverseZeroQuaternionException();
		return conjugate().divideBy(Math.pow(modulus(), 2));
	}

	@Override
	public RealQuaternion copy() {
		return new RealQuaternion(a,b,c,d);
	}

	@Override
	public double modulus() {
		return Math.sqrt(a*a+b*b+c*c+d*d);
	}

	@Override
	public boolean equals(RealQuaternion q) {
		return a==q.a && b==q.b && c==q.c && d==q.d;
	}
	
	public boolean isReal() {
		return b==0 && c==0 && d==0;
	}
	
	public boolean isImaginary() {
		return a==0;
	}
	
	public double[] getComponents() {
		return new double[] {a,b,c,d};
	}
	
	public RealQuaternion conjugate() {
		return new RealQuaternion(a, -b, -c, -d);
	}
	
	public RealQuaternion getReal() {
		return plus(conjugate()).times(1/2d);
	}
	
	public RealQuaternion getImaginary() {
		return minus(conjugate()).times(1/2d);
	}
	
	public RealQuaternion normalize() { // unit quaternion equivalent
		return times(1/modulus());
	}
	
	public Rotator getRotator(double angle) { 
		return new Rotator(angle, this);
	} 
	
	public RealQuaternion conjugateTroll() { // using only quaternion operators
		return plus(I.times(this).times(I))
			.plus(J.times(this).times(J))
			.plus(K.times(this).times(K))
			.times(-1/2d);
	}
	
	public double[] asEulerAngles() {
		return new double[] {
				Math.atan2(2*(a*b+c*d), 1-2*(b*b+c*c)),
				Math.asin(2*(a*c-d*b)),
				Math.atan2(2*(a*d+b*c), 1-2*(c*c+d*d))
		};
	}
	
	public RealComplex[][] asComplexMatrix() {
		return new RealComplex[][] {
			{ new RealComplex(a,b), new RealComplex(c, d) },
			{ new RealComplex(-c,d), new RealComplex(a, -b) }
		};
	}
	
	public double[][] asMatrix() {
		return new double[][] {
			{a, b, c, d},
			{a, -b, c, -d},
			{a, -b, -c, d},
			{a, b, -c, -d}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double[] components = getComponents();
		String sep = " + ", value;
		for (int i=0; i<components.length; i++) 
			if (!(value = parse(i, components[i])).equals(""))
				sb.append(value)
					.append(sep);
		if (sb.toString().equals(""))
			return "0";
		return sb.delete(sb.length()-sep.length(), sb.length())
				.toString();
	}
	
	public static String parse(int order, double value) {
		if (value == 0)
			return "";
		switch (order) {
			case 0: return Double.toString(value);
			case 1:	return value+"i";
			case 2: return value+"j";
			case 3: return value+"k";
			default: throw new IllegalArgumentException("Quaternion dimensions belongs to range [0,3].");
		}
	}
	
	public static class Rotator {
		public final RealQuaternion unit;
		public final Rotation rotation;
		
		private Rotator(double theta, RealQuaternion v) {
			rotation = new Rotation(theta, v);
			double rScalar = Math.cos(theta/2), 
					iScalar = Math.sin(theta/2)/v.modulus();
			unit = new RealQuaternion(rScalar, iScalar*v.b, iScalar*v.c, iScalar*v.d);
		}
		
		public RealQuaternion rotate(RealQuaternion q) {
			return unit.times(q).times(unit.conjugate());
		}
		
		public Rotation recoverAxisAngle() {
			double scalar = Math.sqrt(unit.b*unit.b+unit.c*unit.c+unit.d*unit.d);
			return new Rotation(
					2*Math.atan2(scalar, unit.a),
					new RealQuaternion(0, 1/scalar*unit.b, 1/scalar*unit.c, 1/scalar*unit.d)
			);
		}
	}
	
	public static class Rotation {
		public final double angle;
		public final RealQuaternion axis;
		
		public Rotation(double angle, RealQuaternion axis) {
			this.angle = angle;
			this.axis = axis;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Rotation))
				return false;
			Rotation r = (Rotation) o;
			return angle==r.angle && axis.equals(r.axis);
		}
		
		@Override
		public String toString() {
			return String.format("(%f, %s)", angle, axis.toString());
		}
	}
	
	public static class InverseZeroQuaternionException extends ArithmeticException {
		private static final long serialVersionUID = -5405434972993050733L;
		
		public InverseZeroQuaternionException() {
			super("Cannot inverse the quaternion (0,0,0,0).");
		}
	}
	
	public static class ImaginaryQuaternionRequiredException extends ArithmeticException {
		private static final long serialVersionUID = -7800404838109164036L;

		public ImaginaryQuaternionRequiredException() {
			super("A purely imaginary quaternion is required");
		}
	}
}

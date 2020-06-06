package algebra.concrete;

import algebra.concrete.factories.RealComplexFactory;
import algebra.fields.DivisionElement;

public class RealComplex implements DivisionElement<RealComplex> {
	private final double x, y;
	
	public static RealComplexFactory factory = RealComplexFactory.getInstance();
	public static final RealComplex ZERO = factory.getAdditiveIdentity(),
			ONE = factory.getMultiplicativeIdentity();
	
	public RealComplex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/* Used to create a copy of a complex object */
	public RealComplex(RealComplex c) {
		x = c.x;
		y = c.y;
	}
	
	public RealComplex(double d) {
		this(d, 0);
	}
	
	/* Creates complex 0 */
	public RealComplex() {
		this(0,0);
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
	public RealComplex plus(RealComplex c) {
		return new RealComplex(x+c.x,y+c.y);
	}

	@Override
	public RealComplex times(double lambda) {
		return new RealComplex(x*lambda,y*lambda);
	}

	@Override
	public RealComplex times(RealComplex c) {
		return new RealComplex(x * c.x - y * c.y, x * c.y + y * c.x);
	}

	@Override
	public RealComplex power(int degree) {
		RealComplex total = ONE;
		while (degree-->0) 
			total = total.times(this);
		return total;
	}
	
	@Override
	public RealComplex inverse() {
		if (equals(ZERO))
			throw new InverseZeroComplexException();
		return new RealComplex(x/(x*x+y*y),-y/(x*x+y*y));
	}
	
	@Override
	public double modulus() {
		return Math.sqrt(x*x+y*y);
	}

	@Override
	public RealComplex copy() {
		return new RealComplex(this);
	}

	@Override
	public boolean equals(RealComplex c) {
		return (x==c.x && y==c.y);
	}
	
	//>	Accessors
	public double real() {
		return x;
	}
	
	public double imaginary() {
		return y;
	}

	public RealComplex conjugate() {
		return new RealComplex(x,-y);
	}
	
	public double phase() throws PhaseZeroComplexException{
		if (x>0) 
			return Math.atan(y/x);
		if (x<0 && y>=0) 
			return Math.atan(y/x)+Math.PI;
		if (x<0 && y<0)
			return Math.atan(y/x)-Math.PI;
		if (x==0 && y>0)
			return Math.PI/2;
		if (x==0 && y<0)
			return -Math.PI/2;
		//else 
		throw new PhaseZeroComplexException();
	}
	
	//>	Conversions
	public static RealComplex i2c(int i) {
		return new RealComplex(i,0);
	}
	
	public double[][] matrixify() {
		return new double[][]{
			{x,-y},
			{y,x}
		};
	}
	
	//>	Complex Transformations
	//>>	Translation
	public RealComplex translation(double dx) {
		double nX = x + dx;
		double nY = y;
		return new RealComplex(nX,nY);
	}
	
	//>>	Rotation
	/**
	 * Returns the complex image of a rotation of angle theta.
	 * @param theta	- the angle
	 * @return	complex image
	 */
	public RealComplex rotation(double theta) {
		return rotation(theta,0,0);
	}
	
	/**
	 * Returns the complex image of a rotation characterized by origin (oX,oY) and angle theta.
	 * @param theta	- the angle
	 * @param oX	- abscissa of origin
	 * @param oY	- ordinate of origin
	 * @return	complex image
	 */
	public RealComplex rotation(double theta, double oX, double oY) {
		double nX = oX + Math.cos(theta)*(x-oX) + Math.sin(theta)*(oY-y);
		double nY = oY + Math.cos(theta)*(y-oY) + Math.sin(theta)*(x-oX);
		return new RealComplex(nX,nY);
	}
	
	//>>	Homothety
	/**
	 * Returns the complex image of a homethetic transformation
	 * characterized by origin (oX,oY) and ratio k.
	 * @param k		- ratio
	 * @param oX	- abscissa of origin
	 * @param oY	- ordinate of origin
	 * @return	complex image
	 */
	public RealComplex homothety(double k, double oX, double oY) {
		double nX = oX + k*(x-oX);
		double nY = oY + k*(y-oY);
		return new RealComplex(nX,nY);
	}
	
	//>>	Direct Similarities
	public RealComplex similarity(double k, double theta, double oX, double oY) {
		RealComplex a = new RealComplex(k*Math.cos(theta), k*Math.sin(theta)),		// a = k*[cos(t)+i*sin(t)]
				w = new RealComplex(oX,oY), 									// origin w
				b = (a.times(-1).plus(i2c(1))).times(w);					// b = (1-a)*w
		return a.times(this).plus(b);										// returns a.z + b
	}
	
	/**
	 * Returns z' such as z' = a*z + b with (a,b) belonging to the complex space and a!=0
	 */
	public RealComplex similarity(RealComplex a, RealComplex b) {
		// with ratio k = |a|
		// and angle theta = arg(a)
		// and origin exists iff a!=1 with origin = b/(1-a) 
		return times(a).plus(b);
	}
	
	@Override
	public String toString() {
		String result = "";
		if (x != 0) {
			if (x < 0) result += "-";
			result += (x < 0) ? -x : x;
		} if (y != 0) {
			if (!result.equals("")) {	// if there's a real part
				if (y > 0) result += " + ";
				else result += " - ";
			} if (y != 1 && y != -1) result += (y < 0) ? -y : y;
			result += "i";
		} if (result.equals("")) result = "0";
		return result;
	}
	
	public static final class PhaseZeroComplexException extends ArithmeticException {
		private static final long serialVersionUID = 2320628836150673767L;

		public PhaseZeroComplexException() {
			super("The phase of the complex number z=0 cannot be calculated.");
		}
	}
	
	public static final class InverseZeroComplexException extends ArithmeticException {
		private static final long serialVersionUID = -5405434972993050733L;
		
		public InverseZeroComplexException() {
			super("Cannot inverse the complex (0,0).");
		}
	}
}

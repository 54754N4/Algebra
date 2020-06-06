package algebra.concrete.factories;

import algebra.concrete.Complex;
import algebra.concrete.Real;
import algebra.factory.UnitaryRingFactory;
import algebra.fields.DivisionElement;

public class ComplexFactory<K extends DivisionElement<K>> implements UnitaryRingFactory<Complex<K>>  {
	private final Complex<K> ZERO, ONE;
	
	public ComplexFactory(UnitaryRingFactory<K> factory) {
		K zero = factory.getAdditiveIdentity(),
			one = factory.getMultiplicativeIdentity(); 
		ZERO = new Complex<>(zero, zero);
		ONE = new Complex<>(one, zero);
	}
	
	@Override
	public Complex<K> getAdditiveIdentity() {
		return ZERO;
	}

	@Override
	public Complex<K> getMultiplicativeIdentity() {
		return ONE;
	}
	
	@Override
	public Complex<K> parse(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public static final class Type {
		
		public static ComplexFactory<Real> ofReals() {
			return new ComplexFactory<>(RealFactory.getInstance());
		}
	}
}

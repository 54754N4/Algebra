package algebra.concrete.factories;

import algebra.concrete.RealComplex;
import algebra.factory.UnitaryRingFactory;

public class RealComplexFactory implements UnitaryRingFactory<RealComplex> {
	private static final RealComplexFactory INSTANCE = new RealComplexFactory(); 
	
	public static RealComplexFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public RealComplex getAdditiveIdentity() {
		return new RealComplex();
	}

	@Override
	public RealComplex getMultiplicativeIdentity() {
		return new RealComplex(1);
	}

	@Override
	public RealComplex parse(String input) {
		// TODO Auto-generated method stub
		return null;
	}

}

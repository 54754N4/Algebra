package algebra.concrete.factories;

import algebra.concrete.Real;
import algebra.factory.UnitaryRingFactory;

public class RealFactory implements UnitaryRingFactory<Real> {
	private static final RealFactory INSTANCE = new RealFactory();
	
	public static RealFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Real getAdditiveIdentity() {
		return new Real(0);
	}

	@Override
	public Real getMultiplicativeIdentity() {
		return new Real(1);
	}

	@Override
	public Real parse(String input) {
		return new Real(Double.parseDouble(input));
	}

}

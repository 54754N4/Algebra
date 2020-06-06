package algebra.concrete.factories;

import algebra.concrete.Natural;
import algebra.factory.UnitaryRingFactory;

public class NaturalFactory implements UnitaryRingFactory<Natural> {
	private static final NaturalFactory INSTANCE = new NaturalFactory();
	
	public static NaturalFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Natural getAdditiveIdentity() {
		return new Natural(0);
	}

	@Override
	public Natural getMultiplicativeIdentity() {
		return new Natural(1);
	}
	
	@Override
	public Natural parse(String input) {
		return new Natural(java.lang.Integer.parseInt(input));
	}

}

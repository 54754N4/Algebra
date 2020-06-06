package algebra.concrete.factories;

import algebra.factory.UnitaryRingFactory;
import algebra.concrete.Integer;

public class IntegerFactory implements UnitaryRingFactory<Integer> {
	private static final IntegerFactory INSTANCE = new IntegerFactory();
	
	public static IntegerFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Integer getAdditiveIdentity() {
		return new Integer(0);
	}

	@Override
	public Integer getMultiplicativeIdentity() {
		return new Integer(1);
	}
	
	@Override
	public Integer parse(String input) {
		return new Integer(java.lang.Integer.parseInt(input));
	}

}

package algebra.concrete;

import algebra.concrete.factories.NaturalFactory;

public class Natural extends Integer {
	private static final long serialVersionUID = -8239518170093612335L;

	public static NaturalFactory factory = NaturalFactory.getInstance();
	public static final Natural ZERO = factory.getAdditiveIdentity(),
			ONE = factory.getMultiplicativeIdentity();
	
	public Natural(int x) {
		super(x);
		if (x < 0) 
			throw new NotNaturalNumberException();
	}

	public static class NotNaturalNumberException extends ArithmeticException {
		private static final long serialVersionUID = -4490551536204452280L;

		public NotNaturalNumberException() {
			super("Natural numbers need to be positive.");
		}
	}
}

package algebra.fields;

// Group structures
public interface GroupElement<K extends GroupElement<K>> extends DataElement<K> {
	K plus(K k);
	K times(double lambda);
	boolean isAdditivelyCommutative();
	boolean isAdditiveIdentity();
	
	default K negate() {
		return times(-1);
	}
	
	default K minus(K k) {
		return plus(k.negate());
	}
}

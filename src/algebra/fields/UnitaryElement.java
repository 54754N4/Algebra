package algebra.fields;

// Unitary ring
public interface UnitaryElement<K extends UnitaryElement<K>> extends RingElement<K> {
	boolean isMultiplicativeIdentity();
	
}

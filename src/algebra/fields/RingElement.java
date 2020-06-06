package algebra.fields;

public interface RingElement<K extends RingElement<K>> extends GroupElement<K> {
	K times(K k);
	K power(int degree);
	boolean isMultiplicatevlyCommutative();
} 
package algebra.fields;

public interface DataElement<K extends DataElement<K>> {
	K copy();
	double modulus();
	boolean equals(K k);
}

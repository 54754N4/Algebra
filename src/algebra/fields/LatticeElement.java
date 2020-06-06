package algebra.fields;

// A lattice structure just adds the sorting ability if i remember well
public interface LatticeElement<K extends LatticeElement<K> & Comparable<K>> extends DivisionElement<K> {
	
}
package algebra.fields;

// Division algebra field
public interface DivisionElement<K extends DivisionElement<K>> extends UnitaryElement<K> {
	K inverse();
	
	default K divideBy(double lambda) {
		return times(1/lambda);
	}
	
	default K divideBy(K k) {
		return times(k.inverse());
	}
}

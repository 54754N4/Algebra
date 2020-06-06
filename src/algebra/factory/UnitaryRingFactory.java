package algebra.factory;

public interface UnitaryRingFactory<K> extends GroupFactory<K> {
	K getMultiplicativeIdentity();
}
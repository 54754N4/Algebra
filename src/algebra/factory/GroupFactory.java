package algebra.factory;

public interface GroupFactory<K> extends DataFactory<K> {
	K getAdditiveIdentity();
}
package algebra.factory;


public interface DataFactory<K> {
	K parse(String input);
}